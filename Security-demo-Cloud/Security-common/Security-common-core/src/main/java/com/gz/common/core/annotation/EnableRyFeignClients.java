package com.gz.common.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Description
 * @Author gzzear
 * @Date 2023/07/13 13:06
 * @Version 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableFeignClients
public @interface EnableRyFeignClients
{
  String[] value() default {};

  String[] basePackages() default { "com.gz" };

  Class<?>[] basePackageClasses() default {};

  Class<?>[] defaultConfiguration() default {};

  Class<?>[] clients() default {};
}
