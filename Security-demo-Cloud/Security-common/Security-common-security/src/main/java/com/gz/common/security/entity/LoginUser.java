package com.gz.common.security.entity;

import com.gz.entity.SysUser;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @Description
 * @Author gzzear
 * @Date 2023/07/15 20:12
 * @Version 1.0
 */
public class LoginUser implements UserDetails {

  private SysUser user;

  private final Collection<? extends GrantedAuthority> authorities;

  private final boolean accountNonExpired;

  private final boolean accountNonLocked;

  private final boolean credentialsNonExpired;

  private final boolean enabled;

  public LoginUser(SysUser user, Collection<? extends GrantedAuthority> authorities) {
    this(user, true, true, true, true, authorities);
  }


  public LoginUser(SysUser user, boolean enabled,
      boolean accountNonExpired,
      boolean credentialsNonExpired, boolean accountNonLocked,
      Collection<? extends GrantedAuthority> authorities) {
    this.user = user;
    this.enabled = enabled;
    this.accountNonExpired = accountNonExpired;
    this.credentialsNonExpired = credentialsNonExpired;
    this.accountNonLocked = accountNonLocked;
    this.authorities = authorities;
  }


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  public Long getUserId() {
    return user.getId();
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUserName();
  }

  @Override
  public boolean isAccountNonExpired() {
    return this.accountNonExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return this.accountNonLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return this.credentialsNonExpired;
  }

  @Override
  public boolean isEnabled() {
    return this.enabled;
  }

  public SysUser getUser() {
    return user;
  }

  public void setUser(SysUser user) {
    this.user = user;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof User) {
      return this.user.getUserName().equals(((User) obj).getUsername());
    }
    return false;
  }

  @Override
  public int hashCode() {
    return this.user.getUserName().hashCode();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(getClass().getName()).append(" [");
    sb.append("Username=").append(this.user.getUserName()).append(", ");
    sb.append("Password=[PROTECTED], ");
    sb.append("Enabled=").append(this.enabled).append(", ");
    sb.append("AccountNonExpired=").append(this.accountNonExpired).append(", ");
    sb.append("credentialsNonExpired=").append(this.credentialsNonExpired).append(", ");
    sb.append("AccountNonLocked=").append(this.accountNonLocked).append(", ");
    sb.append("Granted Authorities=").append(this.authorities).append("]");
    return sb.toString();
  }
}
