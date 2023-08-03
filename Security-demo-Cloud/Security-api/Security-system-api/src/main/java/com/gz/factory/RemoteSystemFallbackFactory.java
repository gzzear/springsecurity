package com.gz.factory;

import com.gz.RemoteSystemService;
import com.gz.common.core.entity.R;
import com.gz.entity.UserInfo;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author gzzear
 * @Date 2023/07/13 16:03
 * @Version 1.0
 */
@Component
public class RemoteSystemFallbackFactory implements FallbackFactory<RemoteSystemService> {

  private static final Logger log = LoggerFactory.getLogger(RemoteSystemFallbackFactory.class);


  @Override
  public RemoteSystemService create(Throwable throwable) {
    log.error("用户服务调用失败:{}", throwable.getMessage());
    return new RemoteSystemService() {

      @Override
      public R<UserInfo> fetchUserInfo(String username, String from) {
        return R.fail("获取用户信息失败");
      }

      @Override
      public R<UserInfo> queryUserInfoByPhone(String phone) {
        return R.fail("获取用户信息失败");
      }
    };
  }
}
