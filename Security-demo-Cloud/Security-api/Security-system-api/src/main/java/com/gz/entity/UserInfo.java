package com.gz.entity;

import java.io.Serializable;
import java.util.Set;
import lombok.Data;

/**
 * @Description
 * @Author gzzear
 * @Date 2023/07/15 20:24
 * @Version 1.0
 */
@Data
public class UserInfo implements Serializable {

  private static final long serialVersionUID = 1L;


  private SysUser user;

  private Set<String> permissions;

  private Set<String> roles;


}
