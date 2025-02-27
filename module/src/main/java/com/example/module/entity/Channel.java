package com.example.module.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

/**
* <p>
    * 频道
    * </p>
*
* @author 野狗
* @since 2024-10-15
*/
@Data
@Accessors(chain = true)
public class Channel {

    private BigInteger id;

    private String image;

    private Integer createTime;

    private Integer updateTime;

    private Integer isDeleted;
}
