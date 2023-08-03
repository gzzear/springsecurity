package com.gz.auth.config;

import com.gz.auth.exception.AuthWebResponseExceptionTranslator;
import com.gz.auth.granter.TokenGranterFactory;
import com.gz.auth.service.RedisClientDetailsService;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * @Description oauth2授权服务
 * @Author gzzear
 * @Date 2023/07/31 14:28
 * @Version 1.0
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

  @Resource
  private AuthenticationManager authenticationManager;

  @Resource
  private TokenStore tokenStore;

  @Resource
  private JwtAccessTokenConverter jwtAccessTokenConverter;

  @Resource
  private TokenEnhancer jwtTokenEnhancer;

  @Resource
  private DataSource dataSource;

  @Resource
  private UserDetailsService loginUserDetailsService;

  /**
   * 配置令牌的访问端点和令牌服务
   */
  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    //获取自定义tokenGranter
    TokenGranter tokenGranter = TokenGranterFactory
        .getTokenGranter(authenticationManager, endpoints);
    endpoints
        // 请求方式
        .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
        // 令牌存储方式
        .tokenStore(tokenStore)
        // 指定认证管理器
        .authenticationManager(authenticationManager)
        // 指定token授予者
        .tokenGranter(tokenGranter)
        // 配置userDetailsService（支持刷新token）
        .userDetailsService(loginUserDetailsService)
        // 是否重复使用 refresh_token
        .reuseRefreshTokens(false)
        // 自定义异常处理
        .exceptionTranslator(new AuthWebResponseExceptionTranslator())
        .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    // 扩展token返回结果
    if (jwtAccessTokenConverter != null && jwtTokenEnhancer != null) {
      TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
      List<TokenEnhancer> enhancerList = new ArrayList<>();
      enhancerList.add(jwtTokenEnhancer);
      enhancerList.add(jwtAccessTokenConverter);
      tokenEnhancerChain.setTokenEnhancers(enhancerList);
      // jwt增强
      endpoints.tokenEnhancer(tokenEnhancerChain).accessTokenConverter(jwtAccessTokenConverter);
    }
  }

  /**
   * 声明 ClientDetails实现(redis)
   */
  public RedisClientDetailsService clientDetailsService() {
    return new RedisClientDetailsService(dataSource);
  }

  /**
   * 配置客户端详细信息
   */
  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    // 将client-secret存储在redis中
    clients.withClientDetails(clientDetailsService());
  }

  /**
   * 配置令牌端点的安全配置
   */
  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) {
    security
        .tokenKeyAccess("permitAll()")                    //oauth/token_key是公开
        .checkTokenAccess("permitAll()")                  //oauth/check_token公开
        .allowFormAuthenticationForClients()             //表单认证（申请令牌）
    ;
  }
}
