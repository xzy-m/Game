package com.example.module.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author XRS
 * @date 2025-02-12 下午 4:08
 */
@Configuration
public class DataSourceConfig {

    @Value("${spring.datasource.druid.master.url}")     //从配置文件中注入属性值到字段
    private String dbUrl;
    @Value("${spring.datasource.druid.master.username}")
    private String username;
    @Value("${spring.datasource.druid.master.password}")
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;


    @Value("${spring.datasource.druid.slave1.url}")
    private String slave1DbUrl;
    @Value("${spring.datasource.druid.slave1.username}")
    private String slave1Username;
    @Value("${spring.datasource.druid.slave1.password}")
    private String slave1Password;
    @Value("${spring.datasource.driver-class-name}")
    private String slave1DriverClassName;

    @Value("${spring.datasource.druid.slave2.url}")
    private String slave2DbUrl;
    @Value("${spring.datasource.druid.slave2.username}")
    private String slave2Username;
    @Value("${spring.datasource.druid.slave2.password}")
    private String slave2Password;
    @Value("${spring.datasource.driver-class-name}")
    private String slave2DriverClassName;


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid.master")//使用spring.datasource.master前缀从配置文件中读取属性
    public DataSource masterDataSource() {
        return DataSourceBuilder.create()   //创建数据源实例
                .driverClassName(driverClassName)
                .url(dbUrl)
                .username(username)
                .password(password)
                .build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid.slave1")
    public DataSource slave1DataSource() {

        return DataSourceBuilder.create()
                .driverClassName(slave1DriverClassName)
                .url(slave1DbUrl)
                .username(slave1Username)
                .password(slave1Password)
                .build();

    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid.slave2")
    public DataSource slave2DataSource() {

        return DataSourceBuilder.create()
                .driverClassName(slave2DriverClassName)
                .url(slave2DbUrl)
                .username(slave2Username)
                .password(slave2Password)
                .build();

    }
}
