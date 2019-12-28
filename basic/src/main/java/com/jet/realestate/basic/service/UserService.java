package com.jet.realestate.basic.service;


import com.jet.realestate.basic.dto.UserParam;
import com.jet.realestate.basic.dto.UserQueryParam;
import com.jet.realestate.basic.model.Permission;
import com.jet.realestate.basic.model.Role;
import com.jet.realestate.basic.model.User;
import com.jet.realestate.basic.model.UserRoleRelation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 后台管理员Service
 */
public interface UserService {
    /**
     * 根据用户名获取后台管理员
     */
    User queryUserByName(String username);

    /**
     * 注册功能
     */
    @Transactional
    User register(UserParam UserParam);

    /**
     * 登录功能
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username,String password);

    /**
     * 刷新token的功能
     * @param oldToken 旧的token
     */
    String refreshToken(String oldToken);

    /**
     * 根据用户id获取用户
     */
    User getItem(Long id);

    /**
     * 根据用户名或昵称分页查询用户
     */
    List<User> list(String name, Integer pageSize, Integer pageNum);

    /**
     * 修改指定用户信息
     */
    int update(Long id, User user);

    /**
     * 删除指定用户
     */
    int delete(Long id);

    /**
     * 修改用户角色关系
     */
    @Transactional
    int updateRole(Long userId, List<Long> roleIds);

    /**
     * 获取用户对于角色
     */
    List<Role> queryRolesOfUser(List<Long> userIds);

    /**
     * 修改用户的+-权限
     */
    @Transactional
    int updatePermission(Long userId, List<Long> permissionIds);



    List<UserRoleRelation> queryUserRoleRelation(List<Long> userIds);

    List<User> queryUsersByIds(List<Long> ids);

    List<User> qureyUsers( UserQueryParam params);

    Integer qureyUsersCount( UserQueryParam params);
}
