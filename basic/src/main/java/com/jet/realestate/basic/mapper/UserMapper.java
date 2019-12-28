package com.jet.realestate.basic.mapper;

import com.jet.realestate.basic.dto.UserQueryParam;
import com.jet.realestate.basic.model.User;

import com.jet.realestate.basic.model.UserCriteria;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    long countByExample(UserCriteria example);

    int deleteByExample(UserCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByCriteria(UserCriteria example);

    User selectByName(String name);

    User selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserCriteria example);

    int updateByExample(@Param("record") User record, @Param("example") UserCriteria example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

//    List<User> queryUsersByIds(List<Long> ids);

    List<User> qureyUsers(@Param("params") UserQueryParam params);

    Integer qureyUsersCount(@Param("params") UserQueryParam params);
}