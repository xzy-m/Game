package com.example.module.mapper;

import com.example.module.entity.GameTagRelation;
import com.example.module.entity.Tag;
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
 * @since 2024-09-28
 */
@Mapper
public interface TagMapper {

    @Select("select * from tag where is_deleted = 0")
    List<Tag> getAll();

    @Select("select * from tag where is_deleted = 0 and id = #{id}")
    Tag getById(@Param("id") BigInteger id);

    @Select("select * from tag where id = #{id}")
    Tag extractById(@Param("id") BigInteger id);

    //返回主键id只能是int类型
    int insert(@Param("tag") Tag tag);

    int update(@Param("tag") Tag tag);

    //@Delete("update tag set is_deleted = 1,update_time = #{updateTime} where id = #{id}")
    @Delete("delete from tag where id = #{id}")
    int delete(@Param("updateTime") Integer updateTime, @Param("id") BigInteger id);

    void insertToRelationTable(@Param("relation") GameTagRelation relation);

    List<Tag> getTagsByGameId(@Param("gameId") BigInteger gameId);

    //@Delete("update game_tag_relation set is_deleted = 1,update_time = #{updateTime} where gameId = #{gameId} and tagId = #{tagId}")
    @Delete("delete from game_tag_relation where gameId = #{gameId} and tagId = #{tagId}")
    int deleteRelationRecord(@Param("updateTime") Integer updateTime, @Param("gameId") BigInteger gameId, @Param("tagId") BigInteger tagId);

    @Select("select gameId from game_tag_relation where tagId = #{tagId} and is_deleted = 0")
    List<BigInteger> selectGameIdByTagId(@Param("tagId") BigInteger tagId);

    @Select("select * from tag where name = #{tagName} and is_deleted = 0")
    Tag selectByName(@Param("tagName") String tagName);
}
