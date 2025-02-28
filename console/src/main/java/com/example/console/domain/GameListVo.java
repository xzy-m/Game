package com.example.console.domain;

import lombok.Data;

import java.math.BigInteger;

/**
 * @author XRS
 * @date 2024-07-12 下午 3:33
 */
@Data
public class GameListVo {
    private BigInteger gameId;
    private String picture;
    private String title;
}
