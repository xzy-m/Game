package com.example.module.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

/**
 * @author XRS
 * @date 2024-09-28 下午 7:36
 */
@Data
@Accessors(chain = true)
public class GameTagRelation {
    private BigInteger id;
    private BigInteger gameId;
    private BigInteger tagId;
    private Integer createTime;
    private Integer updateTime;
    private Integer isDeleted;
}
