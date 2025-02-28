package com.example.app.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author XRS
 * @date 2024-07-13 上午 7:41
 */
@Data
@Accessors(chain = true)
public class GameInfoVo {
    private String title;
    private String createTime;
    private List<String> pictures;
    private String downloadLink;
    private List<DetailVo> detail;
    private List<String> tags;
}
