package com.example.module.entity;

import java.math.BigInteger;

import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author 野狗
 * @since 2024-08-10
 */
@Data

public class Category {

    private BigInteger id;
    private String type;
    private Integer createTime;
    private Integer updateTime;
    private Integer isDeleted;
}
