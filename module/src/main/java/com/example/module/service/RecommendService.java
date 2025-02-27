package com.example.module.service;

import com.example.module.entity.Recommend;
import com.example.module.mapper.RecommendMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

/**
 * <p>
 * 推荐 服务类
 * </p>
 *
 * @author 野狗
 * @since 2024-10-15
 */
@Service
public class RecommendService {

    @Resource
    private RecommendMapper recommendMapper;

    public Recommend getById(BigInteger id) {
        return recommendMapper.getById(id);
    }

    public Recommend extractById(BigInteger id) {
        return recommendMapper.extractById(id);
    }

    public int insert(Recommend recommend) {
        return recommendMapper.insert(recommend);
    }

    public int update(Recommend recommend) {
        return recommendMapper.update(recommend);
    }

    public int delete(BigInteger id) {
        int time = (int) (System.currentTimeMillis() / 1000);
        return recommendMapper.delete(time, id);
    }

    public List<Recommend> getAll() {
        return recommendMapper.getAll();
    }
}
