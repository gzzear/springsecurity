package com.gz.auth.provider;


import com.gz.auth.token.SMSVerificationAuthenticationToken;
import com.gz.common.security.entity.LoginUser;
import javax.annotation.Resource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.exceptions.UserDeniedAuthorizationException;

public class SMSAuthenticationProvider implements AuthenticationProvider {

  @Resource
  private UserDetailsService userDetailsService;

  public void setUserDetailsService(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  /**
   * 短信认证
   */
  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    // 获取手机号
    String phone = (authentication.getPrincipal() == null) ? null : authentication.getPrincipal().toString();
    // 获取短信验证码
    String smsCode = (authentication.getCredentials() == null) ? null
        : authentication.getCredentials().toString();
    // todo 在此处进行短信验证码的校验
    // 此处模拟短信验证码为123
    if (!"123".equals(smsCode)) {
      throw new UserDeniedAuthorizationException("验证码不正确");
    }
    LoginUser userDetails = (LoginUser) userDetailsService.loadUserByUsername(phone);
    if (userDetails == null) {
      throw new InternalAuthenticationServiceException("当前手机号不存在,请先注册");
    }
    SMSVerificationAuthenticationToken smsVerificationAuthenticationToken = new SMSVerificationAuthenticationToken(userDetails.getAuthorities(), userDetails, authentication.getCredentials());
    smsVerificationAuthenticationToken.setDetails(authentication.getDetails());
    return smsVerificationAuthenticationToken;
  }

  // 判断是否支持此登录方式
  @Override
  public boolean supports(Class<?> authentication) {
    return (SMSVerificationAuthenticationToken.class.isAssignableFrom(authentication));
  }
}