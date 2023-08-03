package com.gz;

import com.gz.common.core.constants.ServiceNameConstants;
import com.gz.common.core.entity.R;
import com.gz.entity.UserInfo;
import com.gz.factory.RemoteSystemFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import sun.security.util.SecurityConstants;

/**
 * @Description
 * @Author gzzear
 * @Date 2023/07/13 15:58
 * @Version 1.0
 */
@FeignClient(contextId = "remoteSystemService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteSystemFallbackFactory.class)
public interface RemoteSystemService {

  @GetMapping("/sysUser/fetchUserInfo")
  R<UserInfo> fetchUserInfo(@RequestParam("username") String username, @RequestHeader("from") String from);

  @GetMapping("/sysUser/queryUserInfoByPhone")
  R<UserInfo> queryUserInfoByPhone(@RequestParam("phone") String phone);
}
