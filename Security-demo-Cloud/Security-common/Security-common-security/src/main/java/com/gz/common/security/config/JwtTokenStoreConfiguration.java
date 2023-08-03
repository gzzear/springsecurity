package com.gz.common.security.config;


import com.gz.common.security.entity.LoginUser;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * jwt 生成token相关配置
 */
@Configuration
public class JwtTokenStoreConfiguration {

  String SIGNING_KEY = "vtl7U5EUMl4D4rMc";

  /**
   * 使用jwtTokenStore存储token
   */
  @Bean
  public TokenStore jwtTokenStore() {
    return new JwtTokenStore(jwtAccessTokenConverter());
  }

  /**
   * 用于生成jwt
   */
  @Bean
  public JwtAccessTokenConverter jwtAccessTokenConverter() {
    JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
    DefaultAccessTokenConverter defaultAccessTokenConverter = new DefaultAccessTokenConverter();
    defaultAccessTokenConverter.setUserTokenConverter(new LoginUserConverter());
    jwtAccessTokenConverter.setAccessTokenConverter(defaultAccessTokenConverter);
    jwtAccessTokenConverter.setSigningKey(SIGNING_KEY);
    return jwtAccessTokenConverter;
  }

  /**
   * 自定义令牌：用于扩展jwt
   */
  @Bean
  public TokenEnhancer jwtTokenEnhancer() {
    return new TokenEnhancer() {
      @Override
      public OAuth2AccessToken enhance(OAuth2AccessToken accessToken,
          OAuth2Authentication authentication) {
        LoginUser principal = (LoginUser) authentication
            .getUserAuthentication().getPrincipal();
        // todo 以下可根据自身业务自由设置，方便于解析token后获取到有价值的信息
        Map<String, Object> info = new HashMap<>(16);
        info.put("user_id", principal.getUserId());
        info.put("user_name", principal.getUsername());
        info.put("login", true);
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        // todo 此处可以将token存入redis
        return accessToken;
      }
    };
  }

}
