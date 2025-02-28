package com.example.module.entity;

import java.math.BigInteger;
import java.util.List;

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
    private BigInteger parentId;
    private List<Category> children;
    private Integer createTime;
    private Integer updateTime;
    private Integer isDeleted;
}
