package com.example.app.domain;

import lombok.Data;

import java.math.BigInteger;

/**
 * @author XRS
 * @date 2024-07-12 下午 3:33
 */
@Data
public class GameListVo {
    private BigInteger gameId;
    private ImageVO imageVO;
    private String title;
    private String type;
}
