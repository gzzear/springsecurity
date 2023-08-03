package com.gz.common.security.config;

import java.util.Arrays;
import javax.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * @Description 资源服务配置
 * @Author gzzear
 * @Date 2023/07/30 10:06
 * @Version 1.0
 */
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@EnableResourceServer
@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

  @Resource
  private AuthIgnoreConfig authIgnoreConfig;

  @Resource
  private TokenStore tokenStore;

  @Resource
  private JwtAccessTokenConverter jwtAccessTokenConverter;

  @Resource
  private TokenEnhancer jwtTokenEnhancer;


  @Override
  public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    resources.tokenStore(tokenStore)
        .tokenServices(tokenServices())
        .stateless(true);
  }

  public ResourceServerTokenServices tokenServices() {
    DefaultTokenServices services = new DefaultTokenServices();
    services.setSupportRefreshToken(true);
    services.setTokenStore(tokenStore);
    //自定义token增强链
    TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
    tokenEnhancerChain.setTokenEnhancers(Arrays.asList(jwtAccessTokenConverter, jwtTokenEnhancer));
    services.setTokenEnhancer(tokenEnhancerChain);
    return services;
  }


  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();
    ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
        .authorizeRequests();
    // 不登录可以访问的接口
    authIgnoreConfig.getUrls().forEach(url -> registry.antMatchers(url).permitAll());
    registry.anyRequest().authenticated();
  }
}
