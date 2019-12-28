package com.jet.realestate.basic.service;

import com.jet.realestate.basic.dto.PermissionNode;
import com.jet.realestate.basic.model.Permission;

import java.util.List;

/**
 * 后台用户权限管理Service
 */
public interface PermissionService {
    /**
     * 添加权限
     */
    int create(Permission permission);

    /**
     * 修改权限
     */
    int update(Long id,Permission permission);

    /**
     * 批量删除权限
     */
    int delete(List<Long> ids);

    /**
     * 以层级结构返回所有权限
     */
    List<PermissionNode> treeList();

    /**
     * 获取所有权限
     */
    List<Permission> list();

    /**
     * 获取用户所有权限（包括角色权限和+-权限）
     */
    List<Permission> getPermissionList(Long userId);
}
