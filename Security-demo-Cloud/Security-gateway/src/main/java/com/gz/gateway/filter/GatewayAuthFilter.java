package com.gz.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.gz.gateway.config.properties.IgnoreWhiteProperties;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Mr.M
 * @version 1.0
 * @description 网关认证过虑器
 * @date 2022/9/27 12:10
 */
@Component
@Slf4j
public class GatewayAuthFilter implements GlobalFilter, Ordered {

  @Resource
  private TokenStore tokenStore;

  @Resource
  private IgnoreWhiteProperties ignoreWhite;


  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    //请求的url
    String requestUrl = exchange.getRequest().getPath().value();
    AntPathMatcher pathMatcher = new AntPathMatcher();
    //白名单放行
    for (String url : ignoreWhite.getWhites()) {
      if (pathMatcher.match(url, requestUrl)) {
        return chain.filter(exchange);
      }
    }
    //检查token是否存在
    String token = getToken(exchange);
    if (StringUtils.isBlank(token)) {
      return buildReturnMono("没有认证", exchange);
    }
    //判断是否是有效的token
    OAuth2AccessToken oAuth2AccessToken;
    try {
      oAuth2AccessToken = tokenStore.readAccessToken(token);
      boolean expired = oAuth2AccessToken.isExpired();
      if (expired) {
        return buildReturnMono("认证令牌已过期", exchange);
      }
      return chain.filter(exchange);
    } catch (InvalidTokenException e) {
      log.info("认证令牌无效: {}", token);
      return buildReturnMono("认证令牌无效", exchange);
    }

  }

  /**
   * 获取token
   */
  private String getToken(ServerWebExchange exchange) {
    String tokenStr = exchange.getRequest().getHeaders().getFirst("Authorization");
    if (StringUtils.isBlank(tokenStr)) {
      return null;
    }
    String token = tokenStr.split(" ")[1];
    if (StringUtils.isBlank(token)) {
      return null;
    }
    return token;
  }

  private Mono<Void> buildReturnMono(String error, ServerWebExchange exchange) {
    ServerHttpResponse response = exchange.getResponse();
    String jsonString = JSON.toJSONString(new RestErrorResponse(error));
    byte[] bits = jsonString.getBytes(StandardCharsets.UTF_8);
    DataBuffer buffer = response.bufferFactory().wrap(bits);
    response.setStatusCode(HttpStatus.UNAUTHORIZED);
    response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
    return response.writeWith(Mono.just(buffer));
  }


  @Override
  public int getOrder() {
    return 0;
  }


  /**
   * 错误响应参数包装
   */
  class RestErrorResponse implements Serializable {

    private String errMessage;

    public RestErrorResponse(String errMessage) {
      this.errMessage = errMessage;
    }

    public String getErrMessage() {
      return errMessage;
    }

    public void setErrMessage(String errMessage) {
      this.errMessage = errMessage;
    }
  }

}
