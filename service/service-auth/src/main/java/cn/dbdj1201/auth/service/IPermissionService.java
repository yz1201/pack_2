package cn.dbdj1201.auth.service;

import cn.dbdj1201.auth.entity.Permission;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-09
 */
public interface IPermissionService extends IService<Permission> {

    //根据角色获取菜单
    List<Permission> selectAllMenu(String roleId);

    //给角色分配权限
    void saveRolePermissionRelationShip(String roleId, String[] permissionId);

    //根据用户id获取用户菜单
    List<String> selectPermissionValueByUserId(String id);

    List<JSONObject> selectPermissionByUserId(String id);

    //获取全部权限
    List<Permission> queryAllMenu();

    //递归删除菜单
    void removeChildById(String id);

}
