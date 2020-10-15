package com.ycandyz.master.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MyBatis相关配置
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan({"com.ycandyz.master.dao.*"})
public class MyBatisConfig {
}
