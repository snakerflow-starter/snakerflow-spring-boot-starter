package com.github.snakerflow.plugin.starter.example.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.github.snakerflow.plugin.starter.example.entity")
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class MyibatisConfig {
	
	@Primary
	@Bean
	DataSource dataSourceMysql() {
		return DruidDataSourceBuilder.create().build();
	}
}
