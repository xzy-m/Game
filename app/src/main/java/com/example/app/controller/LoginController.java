package com.example.app.controller;

import com.example.app.domain.UserVo;
import com.example.module.response.Response;
import com.example.module.entity.user.User;
import com.example.module.service.UserService;
import com.example.module.tool.AccountTool;
import com.example.module.tool.SignTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

/**
 * @author XRS
 * @date 2024-09-09 下午 4:00
 */
@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    /*
    用户输入手机号和密码
     */
    @RequestMapping("/app/user/login")
    public Response logIn(@RequestParam("phone") String phone,
                          @RequestParam("password") String password) {

        //用户手机和处理过的密码
        String password256 = AccountTool.sha256Hex(password);
        boolean result = userService.accountCheck(phone, password256);

        //账号判断如果为false
        if (!result) {
            Response response = new Response("2001");
            return response;
        }

        //手机合密码没错的用户
        User recordUser = userService.selectByPhone(phone);

        //查到了准备VO   返回用户信息rank
        UserVo userVo = new UserVo();
        userVo.setId(recordUser.getId())
                .setRank(recordUser.getRank())
                .setPhone(recordUser.getPhone());

        //准备sign   sign是一个String且一要有时限，二要被加过密
        String sign = SignTool.getSignByUserId(recordUser.getId());
        userVo.setSign(sign);

        //sign+VO一起返回
        Response<UserVo> response = new Response<>("1001", userVo);
        return response;
    }

    //注册账号
    @RequestMapping("/app/user/register")
    public Response register(@RequestParam("phone") String phone,
                             @RequestParam("password") String password) {

        //生成sign用
        BigInteger userId;

        //注册前要考虑  1，用户已注册过--返回sign    2，用户被禁止--id_Del为1即禁止--返回2001登录失败
        String password256 = AccountTool.sha256Hex(password);

        //写都写了  判断一下手机号
        boolean validPhone = AccountTool.isValidPhone(phone);
        if (!validPhone) {
            Response response = new Response("2001");
            return response;
        }

        //根据查询结果做分别处理
        User recordUser = userService.selectByPhone(phone);
        if (recordUser != null) {
            //被禁
            if (recordUser.getIsDeleted() == 1) {
                return new Response("2001");
            }
            //没被禁  那刷新最后登录ip和时间  不动表了算了
            userId = recordUser.getId();
        } else {
            //注册
            userId = userService.insertOrUpdate(null, phone, password256);
        }

        //返回用户信息
        UserVo userVo = new UserVo();
        userVo.setId(recordUser.getId())
                .setPhone(recordUser.getPhone())
                .setRank(recordUser.getRank())
                .setSign(SignTool.getSignByUserId(userId));

        Response<UserVo> response = new Response<>("1001", userVo);
        return response;
    }
}
