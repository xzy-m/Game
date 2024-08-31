package com.example.app.domain;

import lombok.Data;

import java.util.List;

/**
 * @author XRS
 * @date 2024-07-13 上午 7:41
 */
@Data
public class GameInfoVo {
    private String title;
    private String createTime;
    private List<String> pictures;
    private String downloadLink;
}
