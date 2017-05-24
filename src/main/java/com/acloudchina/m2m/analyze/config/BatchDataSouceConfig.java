package com.acloudchina.m2m.analyze.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * Created by liuhu on 24/03/2017.
 */
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.main")
public class BatchDataSouceConfig extends HikariConfig {
    /**
     * 主数据源
     * @return
     */
    @Primary
    @Bean(name = "mainDataSource")
    public DataSource mainDataSource() {
        return new HikariDataSource(this);
    }
}
