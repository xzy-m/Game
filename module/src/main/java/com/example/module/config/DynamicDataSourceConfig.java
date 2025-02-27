package com.example.module.config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.example.module.enums.DBTypeEnum;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author XRS
 * @date 2025-01-06 下午 8:40
 */
@Configuration
public class DynamicDataSourceConfig {
    @Autowired
    private DataSource masterDataSource;

    @Autowired
    private DataSource slave1DataSource;

    @Autowired
    private DataSource slave2DataSource;

    // 配置动态数据源
    @Bean
    public DataSource dynamicDataSource() {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DBTypeEnum.MASTER, masterDataSource);
        targetDataSources.put(DBTypeEnum.SLAVE1, slave1DataSource);
        targetDataSources.put(DBTypeEnum.SLAVE2, slave2DataSource);

        DynamicRoutingDataSource dynamicDataSource = new DynamicRoutingDataSource();
        dynamicDataSource.setTargetDataSources(targetDataSources);
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource); // 设置默认数据源
        return dynamicDataSource;
    }

    // 配置 MyBatis 的 SqlSessionFactory
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dynamicDataSource) throws Exception {
        MybatisSqlSessionFactoryBean sessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dynamicDataSource);

        // 设置要扫描的 mapper 接口和 XML 文件路径
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        sessionFactoryBean.setTypeAliasesPackage("com.example.module.entity");  // 设置实体类包路径

        return sessionFactoryBean.getObject();
    }

    // 配置 MyBatis 的 SqlSessionTemplate
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
