package com.example.app.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author XRS
 * @date 2024-10-15 下午 12:20
 */
@Data
@Accessors(chain = true)
public class BannerVo {
    private String image;
    private String link;
}
