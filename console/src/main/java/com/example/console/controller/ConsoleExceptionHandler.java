package com.example.console.controller;

import com.example.module.response.Response;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author XRS
 * @date 2024-07-29 上午 12:10
 */
@RestControllerAdvice
public class ConsoleExceptionHandler extends RuntimeException {

    @ExceptionHandler(Exception.class)
    public Response unknownConsoleErrorException(Exception consoleException) {

        Response response = new Response("4004");

        return response;
    }
}
