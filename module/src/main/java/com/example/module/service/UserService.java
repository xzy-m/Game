package com.example.module.service;

import com.example.module.entity.user.User;
import com.example.module.mapper.UserMapper;
import com.example.module.tool.AccountTool;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 野狗
 * @since 2024-09-09
 */
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public User getById(BigInteger id) {
        return userMapper.getById(id);
    }

    public User extractById(BigInteger id) {
        return userMapper.extractById(id);
    }

    public int insert(User user) {
        return userMapper.insert(user);
    }

    public int update(User user) {
        return userMapper.update(user);
    }

    public int delete(BigInteger id) {
        int time = (int) (System.currentTimeMillis() / 1000);
        return userMapper.delete(time, id);
    }

    public User selectByPhoneAndPwd(String phone, String password) {
        return userMapper.selectByPhoneAndPwd(phone, password);
    }

    public BigInteger insertOrUpdate(BigInteger id, String phone, String password) {
        if (password == null || phone == null) {
            throw new RuntimeException("传入参数有null");
        }

        int time = (int) (System.currentTimeMillis() / 1000);
        User user = new User();
        BigInteger resultId;

        user.setUpdateTime(time);
        user.setPassword(password);
        user.setPhone(phone);

        //增或改
        if (id.equals(null)) {
            user.setCreateTime(time);
            user.setIsDeleted(0);
            userMapper.insert(user);
            resultId = user.getId();
        } else {
            if (userMapper.getById(id) == null) {
                throw new RuntimeException("未查询到相关数据，请检查后再试");
            } else {
                user.setUpdateTime(time);
                user.setId(id);
                userMapper.update(user);
                resultId = user.getId();
            }
        }

        return resultId;
    }

    public User selectByPhone(String phone) {
        return userMapper.selectByPhone(phone);
    }

    //判断手机和密码
    public boolean accountCheck(String phone, String password256) {
        //手机号为null--不符合正则:返回false
        if (phone == null || AccountTool.isValidPhone(phone) == false) {
            return false;
        }

        //手机号符合
        User user = userMapper.selectByPhone(phone);

        //查不出用户--密码为null:返false
        if (user == null || user.getPassword() == null) {
            return false;
        }

        return password256.equals(user.getPassword());
    }
}
