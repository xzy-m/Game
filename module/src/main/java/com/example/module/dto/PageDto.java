package com.example.module.dto;

import lombok.Data;

import java.math.BigInteger;

/**
 * @author XRS
 * @date 2024-08-12 上午 2:49
 */
@Data
public class PageDto {
    private BigInteger id;
    private String pictures;
    private String title;
    private String downloadLink;
    private Integer createTime;
    private Integer updateTime;
    private Integer isDeleted;

    private BigInteger categoryId;
    private String type;
}
