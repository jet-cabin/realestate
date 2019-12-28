package com.jet.realestate.basic.controller;

import com.jet.realestate.basic.dto.UserQueryParam;
import com.jet.realestate.basic.service.PermissionService;
import com.jet.realestate.common.api.CommonPage;
import com.jet.realestate.common.api.CommonResult;
import com.jet.realestate.security.bo.AppUserDetails;
import com.jet.realestate.basic.dto.UserLoginParam;
import com.jet.realestate.basic.dto.UserParam;
import com.jet.realestate.basic.model.Role;
import com.jet.realestate.basic.model.User;

import com.jet.realestate.basic.model.UserRoleRelation;
import com.jet.realestate.basic.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 后台用户管理
 */
@Controller
@Api(tags = "UserController", description = "后台用户管理")
@RequestMapping("/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<User> register(@RequestBody UserParam userParam, BindingResult result) {
        User User = userService.register(userParam);
        if (User == null) {
            CommonResult.failed();
        }
        return CommonResult.success(User);
    }

    @ApiOperation(value = "登录以后返回token")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult login(@RequestBody UserLoginParam userLoginParam, BindingResult result) {
        String token = userService.login(userLoginParam.getUsername(), userLoginParam.getPassword());
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation(value = "刷新token")
    @RequestMapping(value = "/token/refresh", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult refreshToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String refreshToken = userService.refreshToken(token);
        if (refreshToken == null) {
            return CommonResult.failed();
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", refreshToken);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation(value = "获取当前登录用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getUserInfo(Principal principal) {
        String username = principal.getName();
        User user = userService.queryUserByName(username);
        Map<String, Object> data = new HashMap<>();
        data.put("username", user.getUsername());
        data.put("roles", queryRoleByUser(Arrays.asList(user.getId())));
        data.put("phone", user.getPhone());
        data.put("address", user.getAddress());
        data.put("email", user.getEmail());
        data.put("nickname", user.getNickname());
        data.put("id", user.getId());
        data.put("note", user.getNote());
        return CommonResult.success(data);
    }

    @GetMapping("/free")
    @ResponseBody
    public User queryUserByName(@RequestParam("name") String name) {
        return userService.queryUserByName(name);
    }

    @ApiOperation(value = "登出功能")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult logout() {
        return CommonResult.success(null);
    }

    @ApiOperation("根据用户名或姓名分页获取用户列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<User>> list(Principal principal, @RequestParam(value = "name", required = false) String name,
                                               @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<User> userList = userService.list(name, pageSize, pageNum);

        userList.remove(userList.stream().filter(u -> u.getId().equals(1L)).findFirst().get());

        AppUserDetails details = (AppUserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        List<Role> roles = queryRoleByUser(Arrays.asList(details.getUser().getId()));

        final String roleAdmin = "admin";
        final String roleStaff = "staff";
        if (roles.get(0).getName().equals(roleAdmin) || roles.get(0).getName().equals(roleStaff)) {
            List<Long> userIds = userList.stream().map(u -> u.getId()).collect(Collectors.toList());

            List<UserRoleRelation> userRoleRelations = userService.queryUserRoleRelation(userIds);

            Long excludeRoleId = roles.get(0).getName().equals(roleAdmin) ? 3L : 2L;


            Set<Long> excludeIds = userRoleRelations.stream().filter(ur -> ur.getRoleId().equals(excludeRoleId)).map(ur -> ur.getUserId()).collect(Collectors.toSet());

            userList = userList.stream().filter(u -> !excludeIds.contains(u.getId())).collect(Collectors.toList());
        }

        return CommonResult.success(CommonPage.restPage(userList));
    }

    @ApiOperation("根据用户名或姓名分页获取用户列表")
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<CommonPage<User>> queryUsers(@RequestBody UserQueryParam params) {
        List<User> userList = userService.qureyUsers(params);

        long total = (long) userService.qureyUsersCount(params);
        CommonPage page = CommonPage.restPage(userList);

        page.setTotal(total);
        return CommonResult.success(page);
    }

    @ApiOperation(value = "根据指定用户主键列表获取用户列表", tags = "获取用户")
    @GetMapping("/free/users")
    @ResponseBody
    public List<User> queryUsersByIds(@ApiParam("用户主键列表，以逗号分隔") @RequestParam("ids") List<Long> ids) {
        List<User> users = userService.queryUsersByIds(ids);

        return users;
    }

    private List<Role> queryRoleByUser(List<Long> userIds) {
        return userService.queryRolesOfUser(userIds);
    }

    @ApiOperation("获取指定用户信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<User> getItem(@PathVariable Long id) {
        User User = userService.getItem(id);
        return CommonResult.success(User);
    }

    @ApiOperation("修改指定用户信息")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@PathVariable Long id, @RequestBody User User) {
        int count = userService.update(id, User);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("删除指定用户信息")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(@PathVariable Long id) {
        int count = userService.delete(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("给用户分配角色")
    @RequestMapping(value = "/role/update", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateRole(@RequestParam("UserId") Long UserId,
                                   @RequestParam("roleIds") List<Long> roleIds) {
        int count = userService.updateRole(UserId, roleIds);
        if (count >= 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("获取指定用户的角色")
    @RequestMapping(value = "/role/{UserId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<Role>> getRoleList(@PathVariable Long UserId) {
        List<Role> roleList = userService.queryRolesOfUser(Arrays.asList(UserId));
        return CommonResult.success(roleList);
    }

    @ApiOperation("给用户分配+-权限")
    @RequestMapping(value = "/permission/update", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updatePermission(@RequestParam Long UserId,
                                         @RequestParam("permissionIds") List<Long> permissionIds) {
        int count = userService.updatePermission(UserId, permissionIds);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
}
