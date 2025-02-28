package com.example.module.response;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * @author XRS
 * @date 2024-09-12 下午 6:32
 */
@Data
@Accessors(chain = true)
public class ResponseStatus {
    private String code;
    private String msg;
}

