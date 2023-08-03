package com.gz.service.impl;

import com.gz.entity.po.SysMenu;
import com.gz.mapper.SysMenuMapper;
import com.gz.mapper.SysRoleMapper;
import com.gz.service.ISysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.Set;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author gzzear
 * @since 2023-07-13
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

  @Resource
  private SysMenuMapper menuMapper;

  @Resource
  private SysRoleMapper roleMapper;

  @Override
  public Set<String> queryPermsByUserId(Long id) {
    return menuMapper.queryPermsByUserId(id);
  }

  @Override
  public Set<String> queryRolesByUserId(Long id) {
    return roleMapper.queryRolesByUserId(id);
  }
}
