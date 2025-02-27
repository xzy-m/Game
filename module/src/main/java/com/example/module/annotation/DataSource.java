package com.example.module.annotation;

import com.example.module.enums.DBTypeEnum;

import java.lang.annotation.*;

/**
 * @author XRS
 * @date 2025-01-06 下午 9:12
 */
@Target({ElementType.METHOD,ElementType.TYPE})  //方法与类可用
@Retention(RetentionPolicy.RUNTIME)   //注解在运行时可用
@Documented
public @interface DataSource {
    //定义一方法type，返回类型DatabaseType，默认设置MASTER
    DBTypeEnum type() default DBTypeEnum.MASTER;
}
