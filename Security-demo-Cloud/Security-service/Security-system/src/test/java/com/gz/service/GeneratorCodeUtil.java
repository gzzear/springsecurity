package com.gz.service;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import java.util.Collections;

/**
 * @Description
 * @Author gzzear
 * @Date 2023/03/27 09:42
 * @Version 1.0
 */
public class GeneratorCodeUtil {

  private String url = "jdbc:mysql://114.132.160.23:3306/blog-cloud?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8";
  private String username = "root";
  private String password = "gaozhe741234";
  private String author = "gzzear";

  public void generateCode() {
    FastAutoGenerator.create(url, username, password)
        .globalConfig(builder -> {
          builder.author(author) // 设置作者
              .fileOverride()
              .enableSwagger() // 开启 swagger 模式
              .outputDir(
                  "/Users/gaozhe/code/Security-Cloud/Security-service/Security-system/src/main/java"); // 指定输出目录
        })
        .packageConfig(builder -> {
          builder.parent("com.gz") // 设置父包名
              .pathInfo(Collections.singletonMap(OutputFile.xml,
                  "/Users/gaozhe/code/Security-Cloud/Security-service/Security-system/src/main/resources/mapper")); // 设置mapperXml生成路径
        })
        .strategyConfig(builder -> {
          builder.addInclude("sys_oauth_client_details") // 设置需要生成的表名
              ; // 设置过滤表前缀
        })
        .templateEngine(new VelocityTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
        .execute();
  }

  public static void main(String[] args) {
    GeneratorCodeUtil util = new GeneratorCodeUtil();
    util.generateCode();
  }
}