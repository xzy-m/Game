package com.example.module.mapper;

import com.example.module.entity.Game;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigInteger;
import java.util.List;

/**
 * @author XRS
 * @date 2024-07-12 下午 4:01
 */
@Mapper
public interface GameMapper {
    @Select("select * from game where is_deleted = 0")
    List<Game> gameAll();

    @Select("select * from game where is_deleted = 0 and id = #{id}")
    Game getById(@Param("id") BigInteger id);

    @Select("select * from game where id = #{id}")
    Game extractById(@Param("id") BigInteger id);

    int insert(@Param("game") Game game);

    int update(@Param("game") Game game);

    @Delete("update game set is_deleted = 1,update_time = #{updateTime} where id = #{id}")
    int delete(@Param("id") BigInteger id, @Param("updateTime") Integer updateTime);

    List<Game> pagination(@Param("title") String title,
                          @Param("categoryIdList") String categoryIdList,
                          @Param("start") int start,
                          @Param("pageSize") int pageSize);

    List<BigInteger> getCategoryIdList(@Param("title") String title);

    int total(@Param("title") String title, @Param("categoryIdList") String categoryIdList);
}
