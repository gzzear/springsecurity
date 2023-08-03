package com.gz.gateway.filter;

import cn.hutool.core.lang.Dict;
import com.gz.common.core.utils.JsonUtils;
import com.gz.common.core.utils.StringUtils;
import com.gz.gateway.config.properties.CaptchaProperties;
import com.gz.gateway.service.ValidateCodeService;
import com.gz.gateway.utils.WebFluxUtils;
import javax.annotation.Resource;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

/**
 * 验证码过滤器
 *
 * @author ruoyi
 */
@Component
public class ValidateCodeFilter extends AbstractGatewayFilterFactory<Object> {

  private final static String[] VALIDATE_URL = new String[]{"/auth/token/formLogin", "/auth/register",
      "/auth/token/smsLogin"};

  @Resource
  private ValidateCodeService validateCodeService;

  @Resource
  private CaptchaProperties captchaProperties;

  private static final String CODE = "code";

  private static final String UUID = "uuid";

  @Override
  public GatewayFilter apply(Object config) {
    return (exchange, chain) -> {
      ServerHttpRequest request = exchange.getRequest();

      // 非登录/注册请求或验证码关闭，不处理
      if (!StringUtils.equalsAnyIgnoreCase(request.getURI().getPath(), VALIDATE_URL)
          || !captchaProperties.getEnabled()) {
        return chain.filter(exchange);
      }

      try {
        String rspStr = WebFluxUtils.resolveBodyFromCacheRequest(exchange);
        Dict obj = JsonUtils.parseMap(rspStr);
        validateCodeService.checkCaptcha(obj.getStr(CODE), obj.getStr(UUID));
      } catch (Exception e) {
        return WebFluxUtils.webFluxResponseWriter(exchange.getResponse(), e.getMessage());
      }
      return chain.filter(exchange);
    };
  }

}