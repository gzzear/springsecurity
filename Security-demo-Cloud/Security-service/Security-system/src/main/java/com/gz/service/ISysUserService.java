package com.gz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gz.entity.SysUser;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author gzzear
 * @since 2023-07-13
 */
public interface ISysUserService extends IService<SysUser> {

  SysUser loadUserByUsername(String username);
}
