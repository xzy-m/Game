package com.example.module.aop;

import com.example.module.annotation.DataSource;
import com.example.module.holder.DBContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author XRS
 * @date 2025-02-13 下午 4:25
 */
@Aspect
@Component
public class DataSourceAspect {
    // 定义切点，匹配使用了 @DataSource 注解的方法
    @Pointcut("@annotation(com.example.module.annotation.DataSource)")
    public void dataSourcePointCut() {
    }

    // 环绕通知，在方法执行前后切换数据源
    @Around("dataSourcePointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        //从连接点获取方法签名信息
        MethodSignature signature = (MethodSignature) point.getSignature();
        //从方法签名中获取实际的方法对象
        Method method = signature.getMethod();

        //从方法上获取 @DataSource 注解
        DataSource dataSource = method.getAnnotation(DataSource.class);
        if (dataSource != null) {
            // 切换数据源类型      什么情况？
            DBContextHolder.setDataSource(dataSource.type());
        }

        try {
            // 执行目标方法
            return point.proceed();
        } finally {
            // 清除数据源类型，确保线程安全
            DBContextHolder.clearDataSource();
        }
    }
}
