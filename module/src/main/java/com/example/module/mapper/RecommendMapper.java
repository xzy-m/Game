package com.example.module.mapper;

import com.example.module.entity.Recommend;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigInteger;
import java.util.List;


/**
 * <p>
 * 推荐 Mapper 接口
 * </p>
 *
 * @author 野狗
 * @since 2024-10-15
 */
@Mapper
public interface RecommendMapper {

    @Select("select * from recommend where is_deleted = 0 and id = #{id}")
    Recommend getById(@Param("id") BigInteger id);

    @Select("select * from recommend where id = #{id}")
    Recommend extractById(@Param("id") BigInteger id);

    int insert(@Param("recommend") Recommend recommend);

    int update(@Param("recommend") Recommend recommend);

    @Delete("update recommend set is_deleted = 1,update_time = #{updateTime} where id = #{id}")
    int delete(@Param("updateTime") Integer updateTime, @Param("id") BigInteger id);

    @Select("select * from recommend where is_deleted = 0")
    List<Recommend> getAll();
}
