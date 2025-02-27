package com.example.module.holder;

import com.example.module.enums.DBTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author XRS
 * @date 2025-01-06 下午 8:23
 * ThreadLocal提供了局部线程，不同线程之间的变量互不干扰
 * 使用set、get、remove方法为当前线程处理变量
 * 原子操作：指不会被线程调度机制打断的操作，这种操作一旦开始，就一直运行到结束，中间不会有任何上下文切换，
 * 确保在多线程环境下数据的一致性和正确性
 * 这个holder是设置数据源的
 */
//AtomicInteger提供了一种在多线程环境下对整数进行原子操作的机制。原子操作是不可分割的操作，不会被其他线程中断
public class DBContextHolder {

    //通过传递类的 .class 字面量，可以确保日志名称的唯一性
    public static final Logger log = LoggerFactory.getLogger(DBContextHolder.class);
    public static final ThreadLocal<DBTypeEnum> context_holder = new ThreadLocal<>();

    //设置数据源变量
    public static void setDataSource(DBTypeEnum dbTypeEnum) {
        //因为 String.format() 方法（或 String.format() 方法）会将 {} 替换为 dbType 变量的 toString() 方法返回的值
        log.info("切换到{}数据源", dbTypeEnum);
        context_holder.set(dbTypeEnum);
    }

    //获取数据源变量
    public static DBTypeEnum getDataSource() {
        return context_holder.get();
    }

    //清除数据源变量
    public static void clearDataSource() {
        context_holder.remove();
    }
}
