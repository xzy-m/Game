package com.example.module.service;

import com.example.module.entity.Sms;
import com.example.module.mapper.SmsMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 野狗
 * @since 2024-10-11
 */
@Service
public class SmsService {

    @Resource
    private SmsMapper smsMapper;

    public Sms getById(BigInteger id) {
        return smsMapper.getById(id);
    }

    public Sms extractById(BigInteger id) {
        return smsMapper.extractById(id);
    }

    public int insert(Sms sms) {
        int time = (int) (System.currentTimeMillis() / 1000);
        sms.setTime(time).setCreateTime(time).setUpdateTime(time).setIsDeleted(0);
        return smsMapper.insert(sms);
    }

    public int update(Sms sms) {
        return smsMapper.update(sms);
    }

    public int delete(BigInteger id) {
        int time = (int) (System.currentTimeMillis() / 1000);
        return smsMapper.delete(time, id);
    }
}
