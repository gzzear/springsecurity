package com.gz.auth.entity.dto;

import lombok.Data;

/**
 * @Description 登录请求体
 * @Author gzzear
 * @Date 2023/08/01 17:13
 * @Version 1.0
 */
@Data
public class LoginReq {

  private String username;

  private String password;

}
