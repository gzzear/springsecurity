package com.gz;

import com.gz.common.core.annotation.EnableRyFeignClients;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description
 * @Author gzzear
 * @Date 2023/07/13 09:50
 * @Version 1.0
 */
@EnableRyFeignClients
@MapperScan(basePackages = "com.gz.**.mapper")
@SpringBootApplication
public class SecuritySystemApplication {

  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(SecuritySystemApplication.class);
//    application.setApplicationStartup(new BufferingApplicationStartup(2048));
    application.run(args);
    System.out.println("(♥◠‿◠)ﾉﾞ  system服务启动成功   ლ(´ڡ`ლ)ﾞ  ");
  }
}
