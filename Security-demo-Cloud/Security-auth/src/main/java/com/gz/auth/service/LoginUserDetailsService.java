package com.gz.auth.service;

import com.gz.RemoteSystemService;
import com.gz.common.core.entity.R;
import com.gz.common.core.enums.UserStatus;
import com.gz.common.core.exception.base.BaseException;
import com.gz.common.core.utils.StringUtils;
import com.gz.common.security.constants.SecurityConstants;
import com.gz.common.security.entity.LoginUser;
import com.gz.entity.UserInfo;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author gzzear
 * @Date 2023/07/15 20:21
 * @Version 1.0
 */
@Service
public class LoginUserDetailsService implements UserDetailsService {

  private static final Logger log = LoggerFactory.getLogger(LoginUserDetailsService.class);

  @Resource
  private RemoteSystemService remoteSystemService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserInfo userInfo = Optional
        .ofNullable(remoteSystemService.fetchUserInfo(username, SecurityConstants.FROM_IN))
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
