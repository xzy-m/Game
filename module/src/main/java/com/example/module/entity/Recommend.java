package com.example.module.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

/**
 * <p>
 * 推荐
 * </p>
 *
 * @author 野狗
 * @since 2024-10-15
 */
@Data
@Accessors(chain = true)
public class Recommend {

    private BigInteger id;

    private String pictures;

    private String title;

    private String downloadLink;

    private Integer createTime;

    private Integer updateTime;

    private Integer isDeleted;
}
