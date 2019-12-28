package com.jet.realestate.basic.mapper;


import com.jet.realestate.basic.model.Permission;
import com.jet.realestate.basic.model.Role;
import com.jet.realestate.basic.model.UserRoleRelation;
import com.jet.realestate.basic.model.UserRoleRelationCriteria;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserRoleRelationMapper {
    long countByExample(UserRoleRelationCriteria example);

    int deleteByExample(UserRoleRelationCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(UserRoleRelation record);

    int insertSelective(UserRoleRelation record);

    List<UserRoleRelation> selectByExample(UserRoleRelationCriteria example);

    UserRoleRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserRoleRelation record, @Param("example") UserRoleRelationCriteria example);

    int updateByExample(@Param("record") UserRoleRelation record, @Param("example") UserRoleRelationCriteria example);

    int updateByPrimaryKeySelective(UserRoleRelation record);

    int updateByPrimaryKey(UserRoleRelation record);


    /**
     * 批量插入用户角色关系
     */
    int insertList(@Param("list") List<UserRoleRelation> adminRoleRelationList);

    /**
     * 获取用于所有角色
     */
    List<Role> getRoleList(@Param("userIds") List<Long> userIds);


}