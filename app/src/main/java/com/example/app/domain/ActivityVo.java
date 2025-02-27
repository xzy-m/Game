package com.example.app.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

/**
 * @author XRS
 * @date 2024-10-15 下午 12:32
 */
@Data
@Accessors(chain = true)
public class ActivityVo {
    private BigInteger id;
    private String image;
    private String title;
}
