package com.gz.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description
 * @Author gzzear
 * @Date 2023/08/02 14:50
 * @Version 1.0
 */
@SpringBootApplication(scanBasePackages = "com.gz")
public class SecurityGatewayApplication {

  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(SecurityGatewayApplication.class);
//    application.setApplicationStartup(new BufferingApplicationStartup(2048));
    application.run(args);
    System.out.println("(♥◠‿◠)ﾉﾞ  Gateway网关启动成功   ლ(´ڡ`ლ)ﾞ  ");
  }
}
