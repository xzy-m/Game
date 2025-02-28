package com.example.console.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XRS
 * @date 2024-07-19 下午 4:16
 */
@Data
public class GameConsolePageVo {
    private Integer total;
    private Integer pageSize;
    private List<GameListVo> pageList;
}
