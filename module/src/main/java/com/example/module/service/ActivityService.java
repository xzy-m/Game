package com.example.module.service;

import com.example.module.entity.Activity;
import com.example.module.mapper.ActivityMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

/**
 * <p>
 * 频道 服务类
 * </p>
 *
 * @author 野狗
 * @since 2024-10-15
 */
@Service
public class ActivityService {
    @Resource
    private ActivityMapper activityMapper;

    public Activity getById(BigInteger id) {
        return activityMapper.getById(id);
    }

    public Activity extractById(BigInteger id) {
        return activityMapper.extractById(id);
    }

    public int insert(Activity activity) {
        return activityMapper.insert(activity);
    }

    public int update(Activity activity) {
        return activityMapper.update(activity);
    }

    public int delete(BigInteger id) {
        int time = (int) (System.currentTimeMillis() / 1000);
        return activityMapper.delete(time, id);
    }

    public List<Activity> getAll() {
        return activityMapper.getAll();
    }
}
