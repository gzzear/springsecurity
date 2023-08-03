package com.gz.auth.service;

import com.gz.common.security.constants.SecurityConstants;
import javax.sql.DataSource;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

/**
 * @Description 重写原生方法支持redis缓存
 * @Author gzzear
 * @Date 2023/07/31 15:58
 * @Version 1.0
 */
public class RedisClientDetailsService extends JdbcClientDetailsService {

  public RedisClientDetailsService(DataSource dataSource) {
    super(dataSource);
    super.setSelectClientDetailsSql(SecurityConstants.DEFAULT_SELECT_STATEMENT);
    super.setFindClientDetailsSql(SecurityConstants.DEFAULT_FIND_STATEMENT);
  }

  @Override
  @Cacheable(value = SecurityConstants.CLIENT_DETAILS_KEY, key = "#clientId", unless = "#result == null")
  public ClientDetails loadClientByClientId(String clientId) {
    return super.loadClientByClientId(clientId);
  }

}