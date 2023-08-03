package com.gz.service;

import com.gz.entity.po.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.Set;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author gzzear
 * @since 2023-07-13
 */
public interface ISysMenuService extends IService<SysMenu> {

  Set<String> queryPermsByUserId(Long id);

  Set<String> queryRolesByUserId(Long id);
}
