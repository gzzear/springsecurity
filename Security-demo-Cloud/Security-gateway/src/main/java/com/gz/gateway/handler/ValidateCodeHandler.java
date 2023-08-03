package com.gz.gateway.handler;

import com.gz.common.core.entity.R;
import com.gz.common.core.exception.CaptchaException;
import com.gz.gateway.service.ValidateCodeService;
import java.io.IOException;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * 验证码获取
 *
 * @author ruoyi
 */
@Component
public class ValidateCodeHandler implements HandlerFunction<ServerResponse> {

  @Resource
  private ValidateCodeService validateCodeService;

  @Override
  public Mono<ServerResponse> handle(ServerRequest serverRequest) {
    R<Map<String, Object>> ajax;
    try {
      ajax = validateCodeService.createCaptcha();
    } catch (CaptchaException | IOException e) {
      return Mono.error(e);
    }
    return ServerResponse.status(HttpStatus.OK).body(BodyInserters.fromValue(ajax));
  }
}
