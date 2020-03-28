package com.github.snakerflow.plugin;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import javax.sql.DataSource;

/**
 * 配置初始化
 * @author zhaoguoqing
 */

@Configuration
@ImportResource({"classpath:applicationContext.xml"})
@AutoConfigureBefore({DruidDataSourceAutoConfigure.class})
public class AutoConfig {

    @Bean
    @ConfigurationProperties("spring.datasource")
    @ConditionalOnMissingBean(name = {"dataSource"})
    public DataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }

}
