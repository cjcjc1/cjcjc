package com.mywebsite.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.mywebsite.dao")
public class MyBatisConfig {

}
