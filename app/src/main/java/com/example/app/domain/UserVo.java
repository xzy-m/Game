package com.example.app.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

/**
 * @author XRS
 * @date 2024-09-15 上午 4:59
 */
@Data
@Accessors(chain = true)
public class UserVo {
    private BigInteger id;
    private String phone;
    private String rank;
    private String password;
    private String sign;
}
