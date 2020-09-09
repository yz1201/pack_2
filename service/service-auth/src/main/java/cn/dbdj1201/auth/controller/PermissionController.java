package cn.dbdj1201.auth.controller;


import cn.dbdj1201.auth.entity.Permission;
import cn.dbdj1201.auth.service.IPermissionService;
import cn.dbdj1201.common.utils.result.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 权限 前端控制器
 * </p>
 *
 * @author dbdj1201
 * @since 2020-09-09
 */
@RestController
@RequestMapping("/admin/acl/permission")
@Slf4j
@Api("权限控制")
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    //获取全部权限
    @ApiOperation(value = "获取全部权限")
    @GetMapping
    public R indexAllPermission() {
        log.info("获取全部权限列表");
        List<Permission> list = this.permissionService.queryAllMenu();
        return R.success().data("children", list);
    }

    @ApiOperation(value = "递归删除权限菜单")
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable("id") String id) {
        log.info("删除{}权限菜单", id);
        this.permissionService.removeChildById(id);
        return R.success();
    }

    @ApiOperation(value = "给角色分配权限")
    @PostMapping("/doAssign")
    public R doAssign(String roleId, String[] permissionId) {
        log.info("给{}角色分配{}权限", roleId, Arrays.asList(permissionId));
        this.permissionService.saveRolePermissionRelationShip(roleId, permissionId);
        return R.success();
    }

    //======================================================================================

    @ApiOperation(value = "根据角色获取权限菜单")
    @GetMapping("toAssign/{roleId}")
    public R toAssign(@PathVariable String roleId) {
        List<Permission> list = permissionService.selectAllMenu(roleId);
        return R.success().data("children", list);
    }


    @ApiOperation(value = "新增权限菜单")
    @PostMapping("save")
    public R save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return R.success();
    }

    @ApiOperation(value = "修改权限菜单")
    @PutMapping("update")
    public R updateById(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return R.success();
    }


    /*
    todo 各个权限模块的重写。此处标记下边的，其他的全部 chrome收藏部门的树形结构搭建，前台模块的数据统计。

     */
}
