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
 * @since 2024-09-28
 */
@Data
@Accessors(chain = true)
public class Tag {
    private BigInteger id;
    private String name;
    private Integer createTime;
    private Integer updateTime;
    private Integer isDeleted;
}
