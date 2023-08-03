package com.gz.gateway.service.impl;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.generator.CodeGenerator;
import cn.hutool.core.util.IdUtil;
import com.gz.common.core.constants.CacheConstants;
import com.gz.common.core.constants.Constants;
import com.gz.common.core.entity.R;
import com.gz.common.core.exception.CaptchaException;
import com.gz.common.core.exception.user.CaptchaExpireException;
import com.gz.common.core.utils.ReflectUtils;
import com.gz.common.core.utils.SpringUtils;
import com.gz.common.core.utils.StringUtils;
import com.gz.common.redis.service.RedisService;
import com.gz.gateway.config.properties.CaptchaProperties;
import com.gz.gateway.enums.CaptchaType;
import com.gz.gateway.service.ValidateCodeService;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Service;

/**
 * 验证码实现处理
 *
 * @author ruoyi
 */
@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {

  @Resource
  private CaptchaProperties captchaProperties;

  @Resource
  private RedisService redisService;

  /**
   * 生成验证码
   */
  @Override
  public R<Map<String, Object>> createCaptcha() throws IOException, CaptchaException {
    Map<String, Object> ajax = new HashMap<>();
    boolean captchaEnabled = captchaProperties.getEnabled();
    ajax.put("captchaEnabled", captchaEnabled);
    if (!captchaEnabled) {
      return R.ok(ajax);
    }
    // 保存验证码信息
    String uuid = IdUtil.simpleUUID();
    String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;
    // 生成验证码
    CaptchaType captchaType = captchaProperties.getType();
    boolean isMath = CaptchaType.MATH == captchaType;
    Integer length = isMath ? captchaProperties.getNumberLength() : captchaProperties.getCharLength();
    CodeGenerator codeGenerator = ReflectUtils.newInstance(captchaType.getClazz(), length);
    AbstractCaptcha captcha = SpringUtils.getBean(captchaProperties.getCategory().getClazz());
    captcha.setGenerator(codeGenerator);
    captcha.createCode();
    String code = captcha.getCode();
    if (isMath) {
      ExpressionParser parser = new SpelExpressionParser();
      Expression exp = parser.parseExpression(StringUtils.remove(code, "="));
      code = exp.getValue(String.class);
    }
    redisService.setCacheObject(verifyKey, code, (int) Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
    ajax.put("uuid", uuid);
    ajax.put("img", captcha.getImageBase64());
    return R.ok(ajax);
  }

  /**
   * 校验验证码
   */
  @Override
  public void checkCaptcha(String code, String uuid) throws CaptchaException {
    if (StringUtils.isEmpty(code)) {
      throw new CaptchaException("user.jcaptcha.not.blank");
    }
    if (StringUtils.isEmpty(uuid)) {
      throw new CaptchaExpireException();
    }
    String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;
    String captcha = redisService.getCacheObject(verifyKey);
    redisService.deleteObject(verifyKey);

    if (!code.equalsIgnoreCase(captcha)) {
      throw new CaptchaException("验证码不正确");
    }
  }
}
