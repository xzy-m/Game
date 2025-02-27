package com.example.app.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XRS
 * @date 2024-07-18 下午 9:21
 */
@Data
public class GameAppPageVo {
    private Boolean isEnd;
    //List不能new，未实例化List方法，直接调用add()，必须先实例化
    private List<GameListVo> pageList;
    private String wrapperParameter;
}
