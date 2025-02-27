package com.example.module.mapper;

import com.example.module.entity.Sms;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigInteger;


/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 野狗
 * @since 2024-10-11
 */
@Mapper
public interface SmsMapper {

    @Select("select * from sms where is_deleted = 0 and id = #{id}")
    Sms getById(@Param("id") BigInteger id);

    @Select("select * from sms where id = #{id}")
    Sms extractById(@Param("id") BigInteger id);

    int insert(@Param("sms") Sms sms);

    int update(@Param("sms") Sms sms);

    @Delete("update sms set is_deleted = 1,update_time = #{updateTime} where id = #{id}")
    int delete(@Param("updateTime") Integer updateTime, @Param("id") BigInteger id);

}
