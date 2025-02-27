package com.example.console.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author XRS
 * @date 2024-09-21 下午 2:23
 */
@Data
@Accessors(chain = true)
public class DetailVo {
    private String type;
    private String content;
}
