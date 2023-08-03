package com.gz.auth.controller;

import com.gz.auth.entity.dto.LoginReq;
import com.gz.auth.entity.vo.LoginResp;
import com.gz.auth.token.SMSVerificationAuthenticationToken;
import com.gz.common.core.entity.R;
import com.gz.common.core.utils.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author gzzear
 * @Date 2023/08/01 17:10
 * @Version 1.0
 */
@RestController
@RequestMapping("/token")
public class TokenController {

  @Resource
  private TokenEndpoint tokenEndpoint;

  @Resource
  private TokenStore tokenStore;

  @PostMapping("/formLogin")
  public R<LoginResp> formLogin(@RequestBody LoginReq loginReq)
      throws HttpRequestMethodNotSupportedException {
    //创建客户端信息,客户端信息可以写死进行处理，因为Oauth2密码模式，客户端双信息必须存在，所以伪装一个
    User clientUser = new User("gzzear", "gzzear123", new ArrayList<>());
    //生成已经认证的client
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(clientUser,
        null, new ArrayList<>());
    //封装成一个UserPassword方式的参数体
    Map<String, String> map = new HashMap<>();
    map.put("username", loginReq.getUsername());
    map.put("password", loginReq.getPassword());
    //授权模式为：密码模式
    map.put("grant_type", "password");
    OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(token, map).getBody();
    LoginResp resp = LoginResp.builder()
        .accessToken(oAuth2AccessToken.getValue())
        .tokenType(oAuth2AccessToken.getTokenType())
        .refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
        .expiresIn(oAuth2AccessToken.getExpiresIn())
        .scope(oAuth2AccessToken.getScope())
        .jti((String) oAuth2AccessToken.getAdditionalInformation().get("jti"))
        .build();
    return R.ok(resp);
  }

  @PostMapping("/smsLogin")
  public R<LoginResp> smsLogin(String phoneNumber, String code)
      throws HttpRequestMethodNotSupportedException {
    User clientUser = new User("gzzear", "gzzear123", new ArrayList<>());
    SMSVerificationAuthenticationToken token = new SMSVerificationAuthenticationToken(
        new ArrayList<>(), clientUser, null);
    Map<String, String> map = new HashMap<>();
    map.put("phone", phoneNumber);
    map.put("SMS-Code", code);
    //授权模式为：短信模式
    map.put("grant_type", "SMSVerification");
    OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(token, map).getBody();
    LoginResp resp = LoginResp.builder()
        .accessToken(oAuth2AccessToken.getValue())
        .tokenType(oAuth2AccessToken.getTokenType())
        .refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
        .expiresIn(oAuth2AccessToken.getExpiresIn())
        .scope(oAuth2AccessToken.getScope())
        .jti((String) oAuth2AccessToken.getAdditionalInformation().get("jti"))
        .build();
    return R.ok(resp);
  }

  @PutMapping("/refresh")
  public R<?> refresh(String refreshToken) throws HttpRequestMethodNotSupportedException {
    String tokenValue = refreshToken.replace(OAuth2AccessToken.BEARER_TYPE, StringUtils.EMPTY).trim();
    User clientUser = new User("gzzear", "gzzear123", new ArrayList<>());
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(clientUser,
        null, new ArrayList<>());
    Map<String, String> map = new HashMap<>();
    map.put("refresh_token", tokenValue);
    map.put("grant_type", "refresh_token");
    OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(token, map).getBody();
    LoginResp resp = LoginResp.builder()
        .accessToken(oAuth2AccessToken.getValue())
        .tokenType(oAuth2AccessToken.getTokenType())
        .refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
        .expiresIn(oAuth2AccessToken.getExpiresIn())
        .scope(oAuth2AccessToken.getScope())
        .jti((String) oAuth2AccessToken.getAdditionalInformation().get("jti"))
        .build();
    return R.ok(resp);
  }


  @PostMapping("/logout")
  public R<?> logout(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String authHeader) {
    String tokenValue = authHeader.replace(OAuth2AccessToken.BEARER_TYPE, StringUtils.EMPTY).trim();
    OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(tokenValue);
    if (oAuth2AccessToken != null) {
      OAuth2RefreshToken oAuth2RefreshToken = oAuth2AccessToken.getRefreshToken();
      //todo 从tokenStore中移除token，用的jwt所以不需要
//      tokenStore.removeAccessToken(oAuth2AccessToken);
//      tokenStore.removeRefreshToken(oAuth2RefreshToken);
//      tokenStore.removeAccessTokenUsingRefreshToken(oAuth2RefreshToken);
      return R.ok("登出成功");
    } else {
      return R.fail("token已失效，请勿重复登出");
    }
  }

}
