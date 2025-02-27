package com.example.module.entity.user;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

/**
 * @author XRS
 * @date 2024-09-15 上午 5:13
 */
@Data
@Accessors(chain = true)
public class UserSign {
    private BigInteger userId;
    private Integer expiration;
    private String salt;
}
