package com.gz.common.mybatis.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Description
 * @Author gzzear
 * @Date 2023/07/11 15:26
 * @Version 1.0
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan("${mybatis-plus.mapperPackage}")
public class MybatisPlusConfiguration {

}
