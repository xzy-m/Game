package com.example.module.mapper;

import com.example.module.entity.Category;
import com.example.module.entity.Game;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigInteger;
import java.util.List;


/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 野狗
 * @since 2024-08-10
 */
@Mapper
public interface CategoryMapper {

    @Select("select * from category where is_deleted = 0 and id = #{id}")
    Category getById(@Param("id") BigInteger id);

    @Select("select * from category where id = #{id}")
    Category extractById(@Param("id") BigInteger id);

    int insert(@Param("category") Category category);

    int update(@Param("category") Category category);

    @Delete("update category set is_deleted = 1,update_time = #{updateTime} where id = #{id}")
    int delete(@Param("updateTime") Integer updateTime, @Param("id") BigInteger id);

    List<Category> getCategoryList(@Param("ids") String ids);

    List<Category> categoryLevel3AndAbove(@Param("id") Integer id);

    List<Category> getAll();

    @Select("select * from category where parent_id is null and is_deleted = 0")
    List<Category> getTops();

    List<Game> getGamesByCategoryId(@Param("ids") List<BigInteger> ids);
}
