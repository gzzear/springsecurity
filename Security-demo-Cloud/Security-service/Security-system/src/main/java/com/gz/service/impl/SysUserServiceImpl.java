package com.gz.service.impl;

import com.gz.common.core.constants.Constants;
import com.gz.entity.SysUser;
import com.gz.mapper.SysUserMapper;
import com.gz.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author gzzear
 * @since 2023-07-13
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements
    ISysUserService {

  @Override
  public SysUser loadUserByUsername(String username) {
    return lambdaQuery()
        .eq(SysUser::getUserName, username)
        .eq(SysUser::getStatus, 0)
        .one();
  }
}
