package com.gz.common.security.handler;

import com.gz.common.core.constants.HttpStatus;
import com.gz.common.core.entity.R;
import com.gz.common.core.exception.CustomException;
import com.gz.common.core.exception.DemoModeException;
import com.gz.common.core.exception.base.BaseException;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 全局异常处理器
 *
 * @author ruoyi
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * 基础异常
   */
  @ExceptionHandler(BaseException.class)
  public R<?> baseException(BaseException e) {
    return R.fail(e.getMessage());
  }

  /**
   * 业务异常
   */
  @ExceptionHandler(CustomException.class)
  public R<?> businessException(CustomException e) {
    if (Objects.isNull(e.getCode())) {
      return R.fail(e.getMessage());
    }
    return R.fail(e.getCode(), e.getMessage());
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  public R<?> handlerNoFoundException(Exception e) {
    log.error(e.getMessage(), e);
    return R.fail(HttpStatus.NOT_FOUND, "路径不存在，请检查路径是否正确");
  }

  @ExceptionHandler(AccessDeniedException.class)
  public R<?> handleAuthorizationException(AccessDeniedException e) {
    log.error(e.getMessage());
    return R.fail(HttpStatus.FORBIDDEN, "没有权限，请联系管理员授权");
  }

  @ExceptionHandler(AccountExpiredException.class)
  public R<?> handleAccountExpiredException(AccountExpiredException e) {
    log.error(e.getMessage(), e);
    return R.fail(e.getMessage());
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public R<?> handleUsernameNotFoundException(UsernameNotFoundException e) {
    log.error(e.getMessage(), e);
    return R.fail(e.getMessage());
  }

  @ExceptionHandler(Exception.class)
  public R<?> handleException(Exception e) {
    log.error(e.getMessage(), e);
    return R.fail(e.getMessage());
  }

  /**
   * 自定义验证异常
   */
  @ExceptionHandler(BindException.class)
  public R<?> validatedBindException(BindException e) {
    log.error(e.getMessage(), e);
    String message = e.getAllErrors().get(0).getDefaultMessage();
    return R.fail(message);
  }

  /**
   * 自定义验证异常
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Object validExceptionHandler(MethodArgumentNotValidException e) {
    log.error(e.getMessage(), e);
    String message = e.getBindingResult().getFieldError().getDefaultMessage();
    return R.fail(message);
  }

  /**
   * 演示模式异常
   */
  @ExceptionHandler(DemoModeException.class)
  public R<?> demoModeException(DemoModeException e) {
    return R.fail("演示模式，不允许操作");
  }
}
