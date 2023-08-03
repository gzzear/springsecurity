package com.gz.service;

import java.util.Set;

/**
 * @Description
 * @Author gzzear
 * @Date 2023/07/15 20:39
 * @Version 1.0
 */
public interface ISysPermissionService {

  Set<String> queryPermsByUserId(Long id);

  Set<String> queryRolesByUserId(Long id);
}
