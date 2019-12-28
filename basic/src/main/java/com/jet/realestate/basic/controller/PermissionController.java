package com.jet.realestate.basic.controller;

import com.jet.realestate.common.api.CommonResult;
import com.jet.realestate.basic.dto.PermissionNode;
import com.jet.realestate.basic.model.Permission;
import com.jet.realestate.basic.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 后台用户权限管理
 */
@Controller
@Api(tags = "PermissionController", description = "后台用户权限管理")
@RequestMapping("/v1/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;
    @ApiOperation("添加权限")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult create(@RequestBody Permission permission) {
        int count = permissionService.create(permission);
        if(count>0){
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改权限")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@PathVariable Long id, @RequestBody Permission permission) {
        int count = permissionService.update(id,permission);
        if(count>0){
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("根据id批量删除权限")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(@RequestParam("ids") List<Long> ids) {
        int count = permissionService.delete(ids);
        if(count>0){
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("以层级结构返回所有权限")
    @RequestMapping(value = "/treeList", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<PermissionNode>> treeList() {
        List<PermissionNode> permissionNodeList = permissionService.treeList();
        return CommonResult.success(permissionNodeList);
    }

    @ApiOperation("获取所有权限列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<Permission>> list() {
        List<Permission> permissionList = permissionService.list();
        return CommonResult.success(permissionList);
    }


    @ApiOperation("获取用户所有权限（包括+-权限）")
    @RequestMapping(value = "/permission/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<Permission>> getPermissionList(@PathVariable("userId") Long userId) {
        List<Permission> permissionList = permissionService.getPermissionList(userId);
        return CommonResult.success(permissionList);
    }

    @RequestMapping(value = "/free/permission/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public List<Permission> getPermissionList1(@PathVariable("userId") Long userId) {
        List<Permission> permissionList = permissionService.getPermissionList(userId);
        return permissionList;
    }
}
