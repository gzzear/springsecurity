package com.gz.gateway.config;

import com.gz.gateway.handler.ValidateCodeHandler;
import javax.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

/**
 * 路由配置信息
 *
 * @author ruoyi
 */
@Configuration
public class RouterFunctionConfiguration {

  @Resource
  private ValidateCodeHandler validateCodeHandler;

  @SuppressWarnings("rawtypes")
  @Bean
  public RouterFunction routerFunction() {
    return RouterFunctions.route(
        RequestPredicates.GET("/code").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
        validateCodeHandler);
  }
}
