package com.gz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author gzzear
 * @since 2023-07-13
 */
@TableName("sys_user")
@ApiModel(value = "SysUser对象", description = "用户表")
public class SysUser implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty("主键")
  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  @ApiModelProperty("用户名")
  private String userName;

  @ApiModelProperty("昵称")
  private String nickName;

  @ApiModelProperty("密码")
  private String password;

  @ApiModelProperty("用户类型：0代表普通用户，1代表管理员")
  private String type;

  @ApiModelProperty("账号状态（0正常 1停用）")
  private String status;

  @ApiModelProperty("邮箱")
  private String email;

  @ApiModelProperty("手机号")
  private String phonenumber;

  @ApiModelProperty("用户性别（0男，1女，2未知）")
  private String sex;

  @ApiModelProperty("头像")
  private String avatar;

  @ApiModelProperty("创建人的用户id")
  private Long createBy;

  @ApiModelProperty("创建时间")
  private LocalDateTime createTime;

  @ApiModelProperty("更新人")
  private Long updateBy;

  @ApiModelProperty("更新时间")
  private LocalDateTime updateTime;

  @ApiModelProperty("删除标志（0代表未删除，1代表已删除）")
  private String delFlag;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhonenumber() {
    return phonenumber;
  }

  public void setPhonenumber(String phonenumber) {
    this.phonenumber = phonenumber;
  }

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public Long getCreateBy() {
    return createBy;
  }

  public void setCreateBy(Long createBy) {
    this.createBy = createBy;
  }

  public LocalDateTime getCreateTime() {
    return createTime;
  }

  public void setCreateTime(LocalDateTime createTime) {
    this.createTime = createTime;
  }

  public Long getUpdateBy() {
    return updateBy;
  }

  public void setUpdateBy(Long updateBy) {
    this.updateBy = updateBy;
  }

  public LocalDateTime getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(LocalDateTime updateTime) {
    this.updateTime = updateTime;
  }

  public String getDelFlag() {
    return delFlag;
  }

  public void setDelFlag(String delFlag) {
    this.delFlag = delFlag;
  }

  @Override
  public String toString() {
    return "SysUser{" +
        "id=" + id +
        ", userName=" + userName +
        ", nickName=" + nickName +
        ", password=" + password +
        ", type=" + type +
        ", status=" + status +
        ", email=" + email +
        ", phonenumber=" + phonenumber +
        ", sex=" + sex +
        ", avatar=" + avatar +
        ", createBy=" + createBy +
        ", createTime=" + createTime +
        ", updateBy=" + updateBy +
        ", updateTime=" + updateTime +
        ", delFlag=" + delFlag +
        "}";
  }

  public SysUser(Long id, String userName) {
    this.id = id;
    this.userName = userName;
  }

  public SysUser() {
  }
}
