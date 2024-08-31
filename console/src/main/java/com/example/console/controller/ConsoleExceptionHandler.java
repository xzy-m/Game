package com.example.console.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author XRS
 * @date 2024-07-29 上午 12:10
 */
@RestControllerAdvice
public class ConsoleExceptionHandler extends RuntimeException {

    @ExceptionHandler(Exception.class)
    public String unknownConsoleErrorException(Exception consoleException) {
        return "未知原因，系统异常";
    }
}
