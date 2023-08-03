package com.gz.auth.service;

import com.gz.RemoteSystemService;
import com.gz.common.core.entity.R;
import com.gz.common.core.enums.UserStatus;
import com.gz.common.core.exception.base.BaseException;
import com.gz.common.core.utils.StringUtils;
import com.gz.common.security.entity.LoginUser;
import com.gz.entity.UserInfo;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SMSUserDetailsService implements UserDetailsService {

  @Resource
  private RemoteSystemService remoteSystemService;

  // 后续登录使用此方法加载用户信息
  @Override
  public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
    // 通过手机号查询用户
    UserInfo userInfo = Optional.of(remoteSystemService.queryUserInfoByPhone(phone))
        .map(R::getData)
        .orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
    checkUserInfo(userInfo);
    return createLoginUser(userInfo);
  }

  private UserDetails createLoginUser(UserInfo userInfo) {
    Set<String> authSet = new HashSet<>(userInfo.getRoles());
    authSet.addAll(userInfo.getPermissions());
    return new LoginUser(userInfo.getUser(),
        AuthorityUtils.createAuthorityList(
            authSet.stream().filter(StringUtils::isNotEmpty).collect(
                Collectors.joining(","))));
  }

  private void checkUserInfo(UserInfo userInfo) {
    if (UserStatus.DELETED.getCode().equals(userInfo.getUser().getDelFlag())) {
      log.info("登录用户：{} 已被删除.", userInfo.getUser().getUserName());
      throw new BaseException("对不起，您的账号：" + userInfo.getUser().getUserName() + " 已被删除");
    } else if (UserStatus.DISABLE.getCode().equals(userInfo.getUser().getStatus())) {
      log.info("登录用户：{} 已被停用.", userInfo.getUser().getUserName());
      throw new BaseException("对不起，您的账号：" + userInfo.getUser().getUserName() + " 已停用");
    }
  }
}
