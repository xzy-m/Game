package com.example.module.entity;

import lombok.Data;

import java.math.BigInteger;

/**
 * @author XRS
 * @date 2024-07-12 下午 3:44
 */
@Data
public class Game {
    //不用基础类型和数组
    private BigInteger id;
    private String pictures;
    private String title;
    private String downloadLink;
    private Integer createTime;
    private Integer updateTime;
    private Integer isDeleted;

    //加一个分类表 game中加一个分类id
    private BigInteger categoryId;
}
