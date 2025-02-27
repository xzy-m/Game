package com.example.module.service;

import com.example.module.entity.Channel;
import com.example.module.mapper.ChannelMapper;
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
public class ChannelService {

    @Resource
    private ChannelMapper channelMapper;

    public Channel getById(BigInteger id) {
        return channelMapper.getById(id);
    }

    public Channel extractById(BigInteger id) {
        return channelMapper.extractById(id);
    }

    public int insert(Channel channel) {
        return channelMapper.insert(channel);
    }

    public int update(Channel channel) {
        return channelMapper.update(channel);
    }

    public int delete(BigInteger id) {
        int time = (int) (System.currentTimeMillis() / 1000);
        return channelMapper.delete(time, id);
    }

    public List<Channel> getAll() {
        return channelMapper.getAll();
    }
}
