package com.gz;

import com.gz.common.core.entity.R;
import com.gz.entity.SysUser;
import javax.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

/**
 * @Description
 * @Author gzzear
 * @Date 2023/07/13 16:36
 * @Version 1.0
 */
@SpringBootTest(classes = {com.gz.auth.SecurityAuthApplication.class})
public class AuthTest {

  @Resource
  private RestTemplate restTemplate;

  @Resource
  private PasswordEncoder passwordEncoder;

  @Test
  public void test() {
    System.out.println(passwordEncoder.encode("gzzear123"));
  }
}
