package com.gz.auth.config;

import com.gz.auth.provider.SMSAuthenticationProvider;
import com.gz.auth.service.LoginUserDetailsService;
import com.gz.auth.service.SMSUserDetailsService;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Description security配置
 * @Author gzzear
 * @Date 2023/07/12 16:04
 * @Version 1.0
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Resource
  private LoginUserDetailsService loginUserDetailsService;

  @Resource
  private SMSUserDetailsService smsUserDetailsService;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean("authenticationManager")
  @Override
  protected AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/oauth/**", "/token/**").permitAll()
        .anyRequest().authenticated()
        .and().formLogin()
        .and()
        .csrf().disable();
    //开启oauth2.0
    http.oauth2Login();
  }

  /**
   * 表单验证provider初始化
   */
  public DaoAuthenticationProvider formAuthenticationProvider(
      @Qualifier("loginUserDetailsService") LoginUserDetailsService loginUserDetailsService) {
    DaoAuthenticationProvider formAuthenticationProvider = new DaoAuthenticationProvider();
    formAuthenticationProvider.setUserDetailsService(loginUserDetailsService);
    formAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    return formAuthenticationProvider;
  }

  /**
   * 短信验证provider初始化
   */
  public SMSAuthenticationProvider smsAuthenticationProvider(
      @Qualifier("SMSUserDetailsService") SMSUserDetailsService smsUserDetailsService) {
    SMSAuthenticationProvider smsAuthenticationProvider = new SMSAuthenticationProvider();
    smsAuthenticationProvider.setUserDetailsService(smsUserDetailsService);
    return smsAuthenticationProvider;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //添加表单authenticationProvider
    auth.authenticationProvider(formAuthenticationProvider(loginUserDetailsService));
    //添加短信authenticationProvider
    auth.authenticationProvider(smsAuthenticationProvider(smsUserDetailsService));
  }


  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring()
        .antMatchers("/css/**", "/js/**", "/index.html", "/img/**", "/fonts/**", "/favicon.ico",
            "/verifyCode");
  }

}
