package com.example.module.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

/**
 * <p>
 *
 * </p>
 *
 * @author 野狗
 * @since 2024-10-11
 */
@Data
@Accessors(chain = true)
public class Sms {
    private BigInteger id;
    private String phone;
    private String content;
    private Integer time;
    private String result;
    private Integer createTime;
    private Integer updateTime;
    private Integer isDeleted;
}
