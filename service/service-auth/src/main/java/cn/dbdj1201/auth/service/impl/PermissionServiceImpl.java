package cn.dbdj1201.auth.service.impl;

import cn.dbdj1201.auth.entity.Permission;
import cn.dbdj1201.auth.entity.RolePermission;
import cn.dbdj1201.auth.entity.User;
import cn.dbdj1201.auth.helper.MemuHelper;
import cn.dbdj1201.auth.helper.PermissionHelper;
import cn.dbdj1201.auth.mapper.PermissionMapper;
import cn.dbdj1201.auth.service.IPermissionService;
import cn.dbdj1201.auth.service.IRolePermissionService;
import cn.dbdj1201.auth.service.IRoleService;
import cn.dbdj1201.auth.service.IUserService;
import cn.dbdj1201.common.service.exception.GOLException;
import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.sql.visitor.functions.If;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-09
 */
@Service
@Slf4j
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Autowired
    private IRolePermissionService rolePermissionService;

    @Autowired
    private IRoleService roleService;

    @Override
    public List<Permission> selectAllMenu(String roleId) {
        return null;
    }

    @Override
    public void saveRolePermissionRelationShip(String roleId, String[] permissionId) {

        /*
        数据有效性的简单校验，权限不做校验，其他模块做严谨一点。
         */
        if (StrUtil.isBlank(roleId)) {
            log.info("给{}角色分配权限,角色呢？", roleId);
            throw new GOLException(20001, "干嘛来了");
        }

        if (this.roleService.getById(roleId) == null) {
            log.info("给{}角色分配权限,角色呢？", roleId);
            throw new GOLException(20001, "干嘛来了");
        }

        /*
        构造RolePermission集合，批量修改中间表
         */
        List<RolePermission> rolePermissions = Arrays.stream(permissionId).map(pid -> {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(pid);
            return rolePermission;
        }).collect(Collectors.toList());

        this.rolePermissionService.saveBatch(rolePermissions);
    }


    @Override
    public List<String> selectPermissionValueByUserId(String id) {
        return null;
    }

    @Override
    public List<JSONObject> selectPermissionByUserId(String id) {
        return null;
    }

    @Override
    public List<Permission> queryAllMenu() {
        List<Permission> permissions = this.list(new QueryWrapper<Permission>().orderByDesc("id"));
        log.info("permissions-{}", permissions);
        return buildPermissions(permissions);
    }

    /**
     * 构建权限菜单树形结构
     *
     * @param permissions
     * @return
     */
    private List<Permission> buildPermissions(List<Permission> permissions) {
        List<Permission> resultNode = new ArrayList<>();
        /*
        获取到顶级菜单分类，开始递归
         */
        permissions.forEach(permission -> {
            if ("0".equals(permission.getPid())) {
                log.info("获取一级菜单");
                permission.setLevel(1);
                resultNode.add(selectChildren(permission, permissions));
            }
        });
        return resultNode;
    }

    /**
     * 递归获取权限菜单内容
     *
     * @param permission
     * @param permissions
     * @return
     */
    private Permission selectChildren(Permission permission, List<Permission> permissions) {
        permission.setChildren(new ArrayList<>());
        permissions.forEach(pNode -> {
            if (permission.getId().equals(pNode.getPid())) {
                Integer level = permission.getLevel();
                pNode.setLevel(level + 1);
                log.info("获取{}级菜单", pNode.getLevel());
                //这一句跟上一句的区别是？
                if (permission.getChildren() == null) {
                    permission.setChildren(new ArrayList<>());
                }
                permission.getChildren().add(selectChildren(pNode, permissions));
            }
        });
        return permission;
    }

    /**
     * 递归删除菜单
     *
     * @param id
     */
    @Override
    public void removeChildById(String id) {
        Permission permission = this.getById(id);
        if (permission == null) {
            throw new GOLException(20001, "走错片场了");
        }
        //记录待删除菜单id
        List<String> ids = new ArrayList<>();
        ids.add(id);
        this.selectPermissionChildById(id, ids);
        this.removeByIds(ids);
    }

    /**
     * 递归删除id顶级菜单之下的所有子子孙孙菜单项
     *
     * @param id
     * @param ids
     */
    private void selectPermissionChildById(String id, List<String> ids) {
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid", id);
        queryWrapper.select("id");

        this.list(queryWrapper).forEach(p -> {
            ids.add(p.getId());
            //递归查询子菜单
            this.selectPermissionChildById(p.getId(), ids);
        });
    }

}
