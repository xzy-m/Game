package com.example.module.service;

import com.example.module.entity.Banner;
import com.example.module.mapper.BannerMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

/**
 * <p>
 * 轮播 服务类
 * </p>
 *
 * @author 野狗
 * @since 2024-10-15
 */
@Service
public class BannerService {

    @Resource
    private BannerMapper bannerMapper;

    public Banner getById(BigInteger id) {
        return bannerMapper.getById(id);
    }

    public Banner extractById(BigInteger id) {
        return bannerMapper.extractById(id);
    }

    public int insert(Banner banner) {
        return bannerMapper.insert(banner);
    }

    public int update(Banner banner) {
        return bannerMapper.update(banner);
    }

    public int delete(BigInteger id) {
        int time = (int) (System.currentTimeMillis() / 1000);
        return bannerMapper.delete(time, id);
    }

    public List<Banner> getAll() {
        return bannerMapper.getAll();
    }
}
