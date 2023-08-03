package com.gz.service.impl;

import com.gz.service.ISysMenuService;
import com.gz.service.ISysPermissionService;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author gzzear
 * @Date 2023/07/15 20:39
 * @Version 1.0
 */
@Service
public class SysPermissionServiceImpl implements ISysPermissionService {

  @Resource
  private ISysMenuService menuService;


  @Override
  public Set<String> queryPermsByUserId(Long id) {
    Set<String> permissions = new HashSet<>();
    if (id == 1) {
      permissions.add("*:*:*");
    } else {
      permissions.addAll(menuService.queryPermsByUserId(id));
    }
    return permissions;
  }

  @Override
  public Set<String> queryRolesByUserId(Long id) {
    Set<String> roles = new HashSet<>();
    if (id == 1) {
      roles.add("admin");
    } else {
      roles.addAll(menuService.queryRolesByUserId(id));
    }
    return roles;
  }
}
