package com.gz.auth;

import com.gz.common.core.annotation.EnableRyFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @Description
 * @Author gzzear
 * @Date 2023/07/11 15:45
 * @Version 1.0
 */
@EnableRyFeignClients
@SpringBootApplication(scanBasePackages = "com.gz")
public class SecurityAuthApplication {

  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(SecurityAuthApplication.class);
//    application.setApplicationStartup(new BufferingApplicationStartup(2048));
    application.run(args);
    System.out.println("(♥◠‿◠)ﾉﾞ  认证授权中心启动成功   ლ(´ڡ`ლ)ﾞ  ");
  }

}
