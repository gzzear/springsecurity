package com.gz.common.security.component;

import cn.hutool.core.util.StrUtil;
import com.gz.common.security.annotation.SecurityInner;
import com.gz.common.security.constants.SecurityConstants;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.security.access.AccessDeniedException;

/**
 * @Description
 * @Author gzzear
 * @Date 2023/08/02 09:42
 * @Version 1.0
 */
@Slf4j
@Aspect
@RequiredArgsConstructor
public class SecurityInnerAspect implements Ordered {

  private final HttpServletRequest request;


  @SneakyThrows
  @Around("@annotation(securityInner)")
  public Object around(ProceedingJoinPoint point, SecurityInner securityInner) {
    String header = request.getHeader(SecurityConstants.FROM);
    if (securityInner.value() && !StrUtil.equals(SecurityConstants.FROM_IN, header)) {
      log.warn("访问接口 {} 没有权限", point.getSignature().getName());
      throw new AccessDeniedException("Access is denied");
    }
    return point.proceed();
  }

  @Override
  public int getOrder() {
    return Ordered.HIGHEST_PRECEDENCE + 1;
  }
}
