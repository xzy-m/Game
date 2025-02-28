package com.example.module.config;

import com.example.module.holder.DBContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author XRS
 * @date 2025-02-23 下午 3:44
 * 这个类的作用是根据当前线程的数据库类型来决定使用哪个数据源
 */
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DBContextHolder.getDataSource();
    }
}
