package com.example.module.mapper;

import com.example.module.entity.Activity;
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
public interface ActivityMapper {

    @Select("select * from activity where is_deleted = 0 and id = #{id}")
    Activity getById(@Param("id") BigInteger id);

    @Select("select * from activity where id = #{id}")
    Activity extractById(@Param("id") BigInteger id);

    int insert(@Param("activity") Activity activity);

    int update(@Param("activity") Activity activity);

    @Delete("update activity set is_deleted = 1,update_time = #{updateTime} where id = #{id}")
    int delete(@Param("updateTime") Integer updateTime, @Param("id") BigInteger id);

    @Select("select * from activity where is_deleted = 0")
    List<Activity> getAll();
}
