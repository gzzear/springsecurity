package com.gz;

import com.gz.entity.SysUser;
import com.gz.service.ISysUserService;
import java.util.List;
import javax.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Description
 * @Author gzzear
 * @Date 2023/07/13 10:53
 * @Version 1.0
 */
@SpringBootTest(classes = {com.gz.SecuritySystemApplication.class})
public class TestService {

  @Resource
  private ISysUserService userService;

  @Test
  public void test1() {
    List<SysUser> list = userService.list();
    System.out.println(list);

  }
}
