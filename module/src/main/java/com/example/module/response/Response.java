package com.example.module.response;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author XRS
 * @date 2024-09-05 下午 5:16
 */
@Data
@Accessors(chain = true)    //所有setter返回的都是this，实现链式调用
public class Response<T> {

    private ResponseStatus status = new ResponseStatus();
    private T data; //装各种VO的

    //可能没有vo，丢个状态码
    public Response(String statusCode) {
        status.setCode(statusCode);
        status.setMsg(ResponseStatusMap.getMSG(statusCode));
        this.data = null;
    }

    //丢个vo，丢个状态码
    public Response(String statusCode, T data) {
        status.setCode(statusCode);
        status.setMsg(ResponseStatusMap.getMSG(statusCode));
        this.data = data;
    }
}
