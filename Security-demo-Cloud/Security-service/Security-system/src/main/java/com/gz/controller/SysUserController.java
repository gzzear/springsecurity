package com.gz.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.gz.common.core.entity.R;
import com.gz.common.security.annotation.SecurityInner;
import com.gz.common.security.entity.LoginUser;
import com.gz.common.security.utils.SecurityUtils;
import com.gz.entity.SysUser;
import com.gz.entity.UserInfo;
import com.gz.service.ISysPermissionService;
import com.gz.service.ISysUserService;
import java.util.Set;
import javax.annotation.Resource;
import org.omg.CORBA.StringHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author gzzear
 * @since 2023-07-13
 */
@RestController
@RequestMapping("/sysUser")
public class SysUserController {

  @Resource
  private ISysUserService userService;

  @Resource
  private ISysPermissionService permissionService;


  @SecurityInner
  @GetMapping("/queryUserInfoByPhone")
  public R<UserInfo> queryUserInfoByPhone(@RequestParam("phone") String phone) {
    //查询对应的SysUser
    SysUser user = userService.lambdaQuery()
        .eq(SysUser::getPhonenumber, phone)
        .eq(SysUser::getStatus, Constants.ZERO)
        .one();
    //查询对应的菜单权限
    Set<String> perms = permissionService.queryPermsByUserId(user.getId());
    //查询对应的角色
    Set<String> roles = permissionService.queryRolesByUserId(user.getId());
    UserInfo userInfo = new UserInfo();
    userInfo.setUser(user);
    userInfo.setPermissions(perms);
    userInfo.setRoles(roles);
    return R.ok(userInfo);
  }

  @GetMapping("/fetchUserInfo")
  public R<UserInfo> fetchUserInfo(@RequestParam("username") String username) {
    //查询对应的SysUser
    SysUser user = userService.lambdaQuery()
        .eq(SysUser::getUserName, username)
        .eq(SysUser::getStatus, Constants.ZERO)
        .one();
    //查询对应的菜单权限
    Set<String> perms = permissionService.queryPermsByUserId(user.getId());
    //查询对应的角色
    Set<String> roles = permissionService.queryRolesByUserId(user.getId());
    UserInfo userInfo = new UserInfo();
    userInfo.setUser(user);
    userInfo.setPermissions(perms);
    userInfo.setRoles(roles);
    return R.ok(userInfo);
  }

  @GetMapping("/hello")
  public String hello() {
    Object principal = SecurityUtils.getAuthentication().getPrincipal();
    return JSONObject.toJSONString(principal);
  }

  @GetMapping("/hello2")
  public String hello2() {
    return "user hello2";
  }
}
