package com.example.module.mapper;

import com.example.module.entity.Channel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigInteger;
import java.util.List;


/**
 * <p>
 * 频道 Mapper 接口
 * </p>
 *
 * @author 野狗
 * @since 2024-10-15
 */
@Mapper
public interface ChannelMapper {

    @Select("select * from channel where is_deleted = 0 and id = #{id}")
    Channel getById(@Param("id") BigInteger id);

    @Select("select * from channel where id = #{id}")
    Channel extractById(@Param("id") BigInteger id);

    int insert(@Param("channel") Channel channel);

    int update(@Param("channel") Channel channel);

    @Delete("update channel set is_deleted = 1,update_time = #{updateTime} where id = #{id}")
    int delete(@Param("updateTime") Integer updateTime, @Param("id") BigInteger id);

    @Select("select * from channel where is_deleted = 0")
    List<Channel> getAll();
}
