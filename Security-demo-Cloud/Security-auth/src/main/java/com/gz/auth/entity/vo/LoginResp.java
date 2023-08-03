package com.gz.auth.entity.vo;

import java.util.Set;
import lombok.Builder;
import lombok.Data;

/**
 * @Description 登录结果返回体
 * @Author gzzear
 * @Date 2023/08/01 17:13
 * @Version 1.0
 */
@Builder
@Data
public class LoginResp {

  private String accessToken;

  private String tokenType;

  private String refreshToken;

  private Integer expiresIn;

  private Set<String> scope;

  private String jti;

}
