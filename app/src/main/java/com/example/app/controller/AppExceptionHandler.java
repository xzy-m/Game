package com.example.app.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author XRS
 * @date 2024-07-29 上午 12:01
 * 程序错误有三类：
 * 1，编译错误，即没有遵循语法规则，编译程序可以自己发现并提示原因与位置
 * 2，运行错误，即程序在执行时，运行环境发现不能执行
 * 3，逻辑错误，即程序没有按照预期执行，当程序发生逻辑错误时，用异常来对这些错误进行处理和控制，姑且认为异常就是一种善后手段
 */
//@RestControllerAdvice   //自定义异常和全局异常处理类是两个东西
public class AppExceptionHandler {

    //异常处理方法，加上这个注解，这个方法就会处理类中其他方法抛出的异常
    //ExceptionHandler注解中可以添加参数，参数就是某个异常类的class，代表该异常类被此方法专门处理;
    //目前理解：service里面有问题只管抛...，controller会接受，只要是这一类型异常，就会通过@ControllerAdvice到这
    @ExceptionHandler(Exception.class)
    public String unknownAppErrorException(Exception appException) {   //最大异常throwable，子类:Exception，error

        //返回一个提示
        return "服务异常";
    }
}
