package com.gz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gz.entity.SysRole;
import java.util.Set;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 角色信息表 Mapper 接口
 * </p>
 *
 * @author gzzear
 * @since 2023-07-13
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

  Set<String> queryRolesByUserId(@Param("userId") Long id);
}
