package com.example.app.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author XRS
 * @date 2024-10-15 下午 12:44
 */
@Data
@Accessors(chain = true)
public class HomePageVo {
    private List<BannerVo> bannerVos;
    private List<ChannelVo> channelVos;
    private List<ActivityVo> activityVos;
    private List<RecommendVo> recommendVos;
}
