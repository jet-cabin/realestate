package com.jet.realestate.basic.mapper;

import com.jet.realestate.basic.model.Permission;
import com.jet.realestate.basic.model.RolePermissionRelation;
import com.jet.realestate.basic.model.RolePermissionRelationCriteria;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolePermissionRelationMapper {
    long countByExample(RolePermissionRelationCriteria example);

    int deleteByExample(RolePermissionRelationCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(RolePermissionRelation record);

    int insertSelective(RolePermissionRelation record);

    List<RolePermissionRelation> selectByExample(RolePermissionRelationCriteria example);

    RolePermissionRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RolePermissionRelation record, @Param("example") RolePermissionRelationCriteria example);

    int updateByExample(@Param("record") RolePermissionRelation record, @Param("example") RolePermissionRelationCriteria example);

    int updateByPrimaryKeySelective(RolePermissionRelation record);

    int updateByPrimaryKey(RolePermissionRelation record);
    /**
     * 批量插入角色和权限关系
     */
    int insertList(@Param("list") List<RolePermissionRelation> list);

    /**
     * 根据角色获取权限
     */
    List<Permission> getPermissionList(@Param("roleId") Long roleId);
}