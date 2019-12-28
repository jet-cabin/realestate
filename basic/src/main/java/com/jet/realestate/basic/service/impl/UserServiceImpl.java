package com.jet.realestate.basic.service.impl;

import com.github.pagehelper.PageHelper;
import com.jet.realestate.basic.dto.UserParam;

import com.jet.realestate.basic.dto.UserQueryParam;
import com.jet.realestate.basic.mapper.UserMapper;
import com.jet.realestate.basic.mapper.UserRoleRelationMapper;

import com.jet.realestate.basic.model.*;
import com.jet.realestate.basic.service.UserService;
import com.jet.realestate.security.util.JwtTokenUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * UserService实现类
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleRelationMapper userRoleRelationMapper;

    
    @Override
    public User queryUserByName(String username) {
       User user=userMapper.selectByName(username);

        return user;
    }

    /**
     * 注册账号
     * 采用BCrypt strong hashing哈希算法进行签名
     * @param userParam
     * @return
     */
    @Override
    public User register(UserParam userParam) {
        User user = new User();
        BeanUtils.copyProperties(userParam, user);
        user.setCreateTime(new Date());
        user.setStatus(1);
        //查询是否有相同用户名的用户
        UserCriteria example = new UserCriteria();
        example.createCriteria().andUsernameEqualTo(user.getUsername());
        List<User> UserList = userMapper.selectByCriteria(example);
        if (UserList.size() > 0) {
            return null;
        }
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        userMapper.insert(user);
        updateRole(user.getId(), Arrays.asList(3L));
        return user;
    }

    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @Override
    public String login(String username, String password) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(!passwordEncoder.matches(password,userDetails.getPassword())){
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            LOGGER.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    /**
     * 添加登录记录
     * @param username 用户名
     */
    private void insertLoginLog(String username) {
//        User admin = getAdminByUsername(username);
//        UserLoginLog loginLog = new UserLoginLog();
//        loginLog.setUserId(admin.getId());
//        loginLog.setCreateTime(new Date());
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        loginLog.setIp(request.getRemoteAddr());
//        loginLogMapper.insert(loginLog);
    }

    /**
     * 根据用户名修改登录时间
     */
    private void updateLoginTimeByUsername(String username) {
        User record = new User();
        record.setLoginTime(new Date());
        UserCriteria example = new UserCriteria();
        example.createCriteria().andUsernameEqualTo(username);
        userMapper.updateByExampleSelective(record, example);
    }

    @Override
    public String refreshToken(String oldToken) {
        String token = oldToken.substring(tokenHead.length());
        if (jwtTokenUtil.canRefresh(token)) {
            return jwtTokenUtil.refreshToken(token);
        }
        return null;
    }

    @Override
    public User getItem(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<User> list(String name, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        UserCriteria example = new UserCriteria();
        UserCriteria.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(name)) {
            criteria.andUsernameLike("%" + name + "%");
            example.or(example.createCriteria().andNickNameLike("%" + name + "%"));
        }

        return userMapper.selectByCriteria(example);
    }

    @Override
    public int update(Long id, User user) {
        user.setId(id);
        //密码已经加密处理，需要单独修改
        user.setPassword(null);
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public int delete(Long id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateRole(Long userId, List<Long> roleIds) {
        int count = roleIds == null ? 0 : roleIds.size();
        //先删除原来的关系
        UserRoleRelationCriteria userRoleRelationCriteria = new UserRoleRelationCriteria();
        userRoleRelationCriteria.createCriteria().anduserIdEqualTo(userId);
        userRoleRelationMapper.deleteByExample(userRoleRelationCriteria);
        //建立新关系
        if (!CollectionUtils.isEmpty(roleIds)) {
            List<UserRoleRelation> list = new ArrayList<>();
            for (Long roleId : roleIds) {
                UserRoleRelation roleRelation = new UserRoleRelation();
                roleRelation.setUserId(userId);
                roleRelation.setRoleId(roleId);
                list.add(roleRelation);
            }
            userRoleRelationMapper.insertList(list);
        }
        return count;
    }

    @Override
    public List<Role> queryRolesOfUser(List<Long> userIds) {
        return userRoleRelationMapper.getRoleList(userIds);
    }

    @Override
    public int updatePermission(Long userId, List<Long> permissionIds) {
        //删除原所有权限关系
//        UserPermissionRelationExample relationExample = new UserPermissionRelationExample();
//        relationExample.createCriteria().andAdminIdEqualTo(adminId);
//        adminPermissionRelationMapper.deleteByExample(relationExample);
//        //获取用户所有角色权限
//        List<Permission> permissionList = adminRoleRelationDao.getRolePermissionList(adminId);
//        List<Long> rolePermissionList = permissionList.stream().map(Permission::getId).collect(Collectors.toList());
//        if (!CollectionUtils.isEmpty(permissionIds)) {
//            List<UserPermissionRelation> relationList = new ArrayList<>();
//            //筛选出+权限
//            List<Long> addPermissionIdList = permissionIds.stream().filter(permissionId -> !rolePermissionList.contains(permissionId)).collect(Collectors.toList());
//            //筛选出-权限
//            List<Long> subPermissionIdList = rolePermissionList.stream().filter(permissionId -> !permissionIds.contains(permissionId)).collect(Collectors.toList());
//            //插入+-权限关系
//            relationList.addAll(convert(adminId,1,addPermissionIdList));
//            relationList.addAll(convert(adminId,-1,subPermissionIdList));
//            return adminPermissionRelationDao.insertList(relationList);
//        }
        return 0;
    }

    /**
     * 将+-权限关系转化为对象
     */
//    private List<UserPermissionRelation> convert(Long adminId,Integer type,List<Long> permissionIdList) {
//        List<UserPermissionRelation> relationList = permissionIdList.stream().map(permissionId -> {
//            UserPermissionRelation relation = new UserPermissionRelation();
//            relation.setUserId(adminId);
//            relation.setType(type);
//            relation.setPermissionId(permissionId);
//            return relation;
//        }).collect(Collectors.toList());
//        return relationList;
//    }

//    @Override
//    public List<Permission> getPermissionList(Long userId) {
//        return userRoleRelationMapper.getPermissionList(userId);
//    }

    public List<UserRoleRelation> queryUserRoleRelation(List<Long> userIds){
        UserRoleRelationCriteria criteria = new UserRoleRelationCriteria();

        criteria.createCriteria().anduserIdIn(userIds);

        return userRoleRelationMapper.selectByExample(criteria);
    }

    @Override
    public List<User> queryUsersByIds(List<Long> ids) {
      UserCriteria criteria=new UserCriteria();
      criteria.createCriteria().andIdIn(ids);

      return userMapper.selectByCriteria(criteria);
    }

    @Override
    public List<User> qureyUsers(UserQueryParam params) {
        return userMapper.qureyUsers(params);
    }

    @Override
    public Integer qureyUsersCount(UserQueryParam params) {
        return userMapper.qureyUsersCount(params);
    }
}
