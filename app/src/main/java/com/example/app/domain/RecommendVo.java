package com.example.app.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

/**
 * @author XRS
 * @date 2024-10-15 下午 12:36
 */
@Data
@Accessors(chain = true)
public class RecommendVo {
    private BigInteger id;
    private String pictures;
    private String title;
    private String downloadLink;
}
