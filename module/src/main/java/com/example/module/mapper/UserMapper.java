package com.example.module.mapper;

import com.example.module.entity.user.User;
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
 * @since 2024-09-09
 */
@Mapper
public interface UserMapper {

    @Select("select * from user where is_deleted = 0 and id = #{id}")
    User getById(@Param("id") BigInteger id);

    @Select("select * from user where id = #{id}")
    User extractById(@Param("id") BigInteger id);

    int insert(@Param("user") User user);

    int update(@Param("user") User user);

    @Delete("update user set is_deleted = 1,update_time = #{updateTime} where id = #{id}")
    int delete(@Param("updateTime") Integer updateTime, @Param("id") BigInteger id);

    @Select("select * from user where phone = #{phone} and password = #{password} and is_deleted = 0")
    User selectByPhoneAndPwd(@Param("phone") String phone, @Param("password") String password);

    @Select("select * from user where phone = #{phone} and is_deleted = 0")
    User selectByPhone(String phone);
}
