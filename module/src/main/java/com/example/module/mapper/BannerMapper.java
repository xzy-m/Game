package com.example.module.mapper;

import com.example.module.entity.Banner;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigInteger;
import java.util.List;


/**
 * <p>
 * 轮播 Mapper 接口
 * </p>
 *
 * @author 野狗
 * @since 2024-10-15
 */
@Mapper
public interface BannerMapper {

    @Select("select * from banner where is_deleted = 0 and id = #{id}")
    Banner getById(@Param("id") BigInteger id);

    @Select("select * from banner where id = #{id}")
    Banner extractById(@Param("id") BigInteger id);

    int insert(@Param("banner") Banner banner);

    int update(@Param("banner") Banner banner);

    @Delete("update banner set is_deleted = 1,update_time = #{updateTime} where id = #{id}")
    int delete(@Param("updateTime") Integer updateTime, @Param("id") BigInteger id);

    @Select("select * from banner where is_deleted = 0")
    List<Banner> getAll();
}
