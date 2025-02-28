package com.example.app.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author XRS
 * @date 2025-01-23 下午 6:57
 */

//@Configuration
public class DataSourceConfig {

    //@ConfigurationProperties(prefix = "spring.datasource.master")
    //这个注解用于将配置文件中的属性绑定到Java对象上。
    //它通常与@Configuration注解的类一起使用，用于从外部配置文件（如application.properties或application.yml）中读取配置，并将其自动注入到对应的Java对象属性中
    /*@Bean(name = "masterDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.master")
    public DataSource masterDataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "slave1DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.slave1")
    public DataSource slave1DataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "slave2DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.slave2")
    public DataSource slave2DataSource() {
        return new DruidDataSource();
    }

    //@Qualifier("masterDataSource")
    //这个注解用于指定要注入的Bean的具体实例。在Spring中，如果存在多个相同类型的Bean，@Autowired注解可能无法确定需要注入哪一个实例，这时可以使用@Qualifier注解来明确指定
    @Bean
    public DataSource dynamicDataSource(@Qualifier("masterDataSource") DataSource masterDataSource,
                                        @Qualifier("slave1DataSource") DataSource slave1DataSource,
                                        @Qualifier("slave2DataSource") DataSource slave2DataSource) {
        return null;
    }*/
}
