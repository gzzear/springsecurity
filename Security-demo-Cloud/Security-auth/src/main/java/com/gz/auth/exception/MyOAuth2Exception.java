package com.gz.auth.exception;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @Description 自定义OAuth2Exception
 * @Author gzzear
 * @Date 2023/08/01 16:20
 * @Version 1.0
 */
@JsonSerialize(using = MyOauthExceptionJackson2Serializer.class)
@JsonDeserialize(using = MyOAuth2ExceptionJackson2Deserializer.class)
public class MyOAuth2Exception extends OAuth2Exception {
  public MyOAuth2Exception(String msg, Throwable t) {
    super(msg, t);
  }

  public MyOAuth2Exception(String msg) {
    super(msg);
  }
}
