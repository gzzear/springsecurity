package com.gz.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * @Description 授权终端表 sys_oauth_client_details
 * @Author gzzear
 * @Date 2023/08/01 19:53
 * @Version 1.0
 */
@ApiModel(value = "SysClientDetails", description = "授权终端表")
@TableName("sys_oauth_client_details")
public class SysClientDetails implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 终端编号
   */
  @TableField("client_id")
  @ApiModelProperty("终端编号")
  private String clientId;

  /**
   * 资源ID标识
   */
  @TableField("resource_ids")
  @ApiModelProperty("资源ID标识")
  private String resourceIds;

  /**
   * 终端安全码
   */
  @TableField("client_secret")
  @ApiModelProperty("终端安全码")
  private String clientSecret;

  /**
   * 终端授权范围
   */
  @TableField("scope")
  @ApiModelProperty("终端授权范围")
  private String scope;

  /**
   * 终端授权类型
   */
  @TableField("authorized_grant_types")
  @ApiModelProperty("终端授权类型")
  private String authorizedGrantTypes;

  /**
   * 服务器回调地址
   */
  @TableField("web_server_redirect_uri")
  @ApiModelProperty("服务器回调地址")
  private String webServerRedirectUri;

  /**
   * 访问资源所需权限
   */
  @TableField("authorities")
  @ApiModelProperty("访问资源所需权限")
  private String authorities;

  /**
   * 设定终端的access_token的有效时间值（秒）
   */
  @TableField("access_token_validity")
  @ApiModelProperty("设定终端的access_token的有效时间值（秒）")
  private Integer accessTokenValidity;

  /**
   * 设定终端的refresh_token的有效时间值（秒）
   */
  @TableField("refresh_token_validity")
  @ApiModelProperty("设定终端的refresh_token的有效时间值（秒）")
  private Integer refreshTokenValidity;

  /**
   * 附加信息
   */
  @TableField("additional_information")
  @ApiModelProperty("附加信息")
  private String additionalInformation;

  /**
   * 是否登录时跳过授权
   */
  @TableField("autoapprove")
  @ApiModelProperty("是否登录时跳过授权")
  private String autoapprove;


  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public String getResourceIds() {
    return resourceIds;
  }

  public void setResourceIds(String resourceIds) {
    this.resourceIds = resourceIds;
  }

  public String getClientSecret() {
    return clientSecret;
  }

  public void setClientSecret(String clientSecret) {
    this.clientSecret = clientSecret;
  }

  public String getScope() {
    return scope;
  }

  public void setScope(String scope) {
    this.scope = scope;
  }

  public String getAuthorizedGrantTypes() {
    return authorizedGrantTypes;
  }

  public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
    this.authorizedGrantTypes = authorizedGrantTypes;
  }

  public String getWebServerRedirectUri() {
    return webServerRedirectUri;
  }

  public void setWebServerRedirectUri(String webServerRedirectUri) {
    this.webServerRedirectUri = webServerRedirectUri;
  }

  public String getAuthorities() {
    return authorities;
  }

  public void setAuthorities(String authorities) {
    this.authorities = authorities;
  }

  public Integer getAccessTokenValidity() {
    return accessTokenValidity;
  }

  public void setAccessTokenValidity(Integer accessTokenValidity) {
    this.accessTokenValidity = accessTokenValidity;
  }

  public Integer getRefreshTokenValidity() {
    return refreshTokenValidity;
  }

  public void setRefreshTokenValidity(Integer refreshTokenValidity) {
    this.refreshTokenValidity = refreshTokenValidity;
  }

  public String getAdditionalInformation() {
    return additionalInformation;
  }

  public void setAdditionalInformation(String additionalInformation) {
    this.additionalInformation = additionalInformation;
  }

  public String getAutoapprove() {
    return autoapprove;
  }

  public void setAutoapprove(String autoapprove) {
    this.autoapprove = autoapprove;
  }
}
