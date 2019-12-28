package com.jet.realestate.basic.service.impl;

import com.jet.realestate.basic.dto.PermissionNode;
import com.jet.realestate.basic.mapper.PermissionMapper;
import com.jet.realestate.basic.model.Permission;
import com.jet.realestate.basic.model.PermissionCriteria;
import com.jet.realestate.basic.service.PermissionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 后台用户权限管理Service实现类
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public int create(Permission permission) {
        permission.setStatus(1);
        permission.setCreateTime(new Date());
        permission.setSort(0);
        return permissionMapper.insert(permission);
    }

    @Override
    public int update(Long id, Permission permission) {
        permission.setId(id);
        return permissionMapper.updateByPrimaryKey(permission);
    }

    @Override
    public int delete(List<Long> ids) {
        PermissionCriteria example = new PermissionCriteria();
        example.createCriteria().andIdIn(ids);
        return permissionMapper.deleteByExample(example);
    }

    @Override
    public List<PermissionNode> treeList() {
        List<Permission> permissionList = permissionMapper.selectByExample(new PermissionCriteria());
        List<PermissionNode> result = permissionList.stream()
                .filter(permission -> permission.getPid().equals(0L))
                .map(permission -> covert(permission, permissionList)).collect(Collectors.toList());
        return result;
    }

    @Override
    public List<Permission> list() {
        return permissionMapper.selectByExample(new PermissionCriteria());
    }

    /**
     * 将权限转换为带有子级的权限对象
     * 当找不到子级权限的时候map操作不会再递归调用covert
     */
    private PermissionNode covert(Permission permission, List<Permission> permissionList) {
        PermissionNode node = new PermissionNode();
        BeanUtils.copyProperties(permission, node);
        List<PermissionNode> children = permissionList.stream()
                .filter(subPermission -> subPermission.getPid().equals(permission.getId()))
                .map(subPermission -> covert(subPermission, permissionList)).collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }

    /**
     * 获取用户所有角色权限
     */
    public List<Permission> getRolePermissionList(Long userId) {
        return permissionMapper.getRolePermissionList(userId);
    }

    /**
     * 获取用户所有权限(包括+-权限)
     */
    public List<Permission> getPermissionList(Long userId) {
        return permissionMapper.getPermissionList(userId);
    }
}
