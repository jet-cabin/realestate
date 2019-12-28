package com.jet.realestate.basic.service.impl;

import com.jet.realestate.basic.mapper.RoleMapper;
import com.jet.realestate.basic.mapper.RolePermissionRelationMapper;

import com.jet.realestate.basic.model.*;
import com.jet.realestate.basic.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 后台角色管理Service实现类
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RolePermissionRelationMapper rolePermissionRelationMapper;

    @Override
    public int create(Role role) {
        role.setCreateTime(new Date());
        role.setStatus(1);
        role.setAdminCount(0);
        role.setSort(0);
        return roleMapper.insert(role);
    }

    @Override
    public int update(Long id, Role role) {
        role.setId(id);
        return roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public int delete(List<Long> ids) {
        RoleCriteria example = new RoleCriteria();
        example.createCriteria().andIdIn(ids);
        return roleMapper.deleteByExample(example);
    }

    @Override
    public List<Permission> getPermissionList(Long roleId) {
        return rolePermissionRelationMapper.getPermissionList(roleId);
    }

    @Override
    public int updatePermission(Long roleId, List<Long> permissionIds) {
        //先删除原有关系
        RolePermissionRelationCriteria example=new RolePermissionRelationCriteria();
        example.createCriteria().andRoleIdEqualTo(roleId);
        rolePermissionRelationMapper.deleteByExample(example);
        //批量插入新关系
        List<RolePermissionRelation> relationList = new ArrayList<>();
        for (Long permissionId : permissionIds) {
            RolePermissionRelation relation = new RolePermissionRelation();
            relation.setRoleId(roleId);
            relation.setPermissionId(permissionId);
            relationList.add(relation);
        }
        return rolePermissionRelationMapper.insertList(relationList);
    }

    @Override
    public List<Role> list() {
        return roleMapper.selectByExample(new RoleCriteria());
    }
}
