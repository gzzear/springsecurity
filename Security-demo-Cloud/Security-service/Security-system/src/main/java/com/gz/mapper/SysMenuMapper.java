package com.gz.mapper;

import com.gz.entity.po.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.Set;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author gzzear
 * @since 2023-07-13
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

  Set<String> queryPermsByUserId(@Param("userId") Long id);
}
