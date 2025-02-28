package com.example.console.controller;

import com.example.console.domain.UserVo;
import com.example.module.entity.user.User;
import com.example.module.response.Response;
import com.example.module.service.UserService;
import com.example.module.tool.AccountTool;
import com.example.module.tool.SignTool;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author XRS
 * @date 2024-09-09 下午 7:52
 */
@RestController
public class LoginController {

    @Autowired
    private UserService userService;
    /*
        1,HttpSession是Java Servlet API的一部分，用于在多个页面请求或访问之间保持用户的状态信息。
        它是一个存储在服务器端的对象，每个用户（每个浏览器实例）访问Web应用时，服务器都会为其创建一个独立的HttpSession对象
        HttpSession session = request.getSession();
        session.setAttribute("phone",phone);
        session.setAttribute("password",password);
        Session 不是放在客户端浏览器里面的。Session 是存储在服务器端的
    */

    @RequestMapping("/console/user/login")
    public Response logIn(@RequestParam("phone") String phone,
                          @RequestParam("password") String password,
                          HttpServletResponse servletResponse) {

        //处理用户密码  判断用户
        String password256 = AccountTool.sha256Hex(password);
        boolean result = userService.accountCheck(phone, password256);

        //账号判断如果为false
        if (!result) {
            Response response = new Response("2001");
            return response;
        }

        User user = userService.selectByPhone(phone);
        //查到了  返回cookie  sign为value
        String sign = SignTool.getSignByUserId(user.getId());
        Cookie cookie = new Cookie("sign", sign);

        //返回vo  用户信息目前一个字段rank
        UserVo userVo = new UserVo();
        userVo.setId(user.getId())
                .setPhone(user.getPhone())
                .setRank(user.getRank());

        //设置时限
        cookie.setMaxAge(60 * 60 * 24);
        servletResponse.addCookie(cookie);

        //servletRequest.getSession().setAttribute("sign", sign);

        Response<UserVo> response = new Response("1001", userVo);
        return response;

    }

    @RequestMapping("/console/user/register")
    public Response register(@RequestParam("phone") String phone,
                             @RequestParam("password") String password) {

        //拿手机号  密码加密
        boolean validPhone = AccountTool.isValidPhone(phone);
        if (!validPhone) {
            Response response = new Response("2001");
            return response;
        }
        String password256 = AccountTool.sha256Hex(password);

        //已经注册过了或者用户被禁止
        User recordUser = userService.selectByPhone(phone);
        if (recordUser != null || recordUser.getIsDeleted() == 1) {
            return new Response("1005");
        }

        //添加数据库即注册
        userService.insertOrUpdate(null, phone, password256);

        //返回用户信息复制粘贴
        User user = userService.selectByPhone(phone);
        UserVo userVo = new UserVo();
        userVo.setId(user.getId())
                .setPhone(user.getPhone())
                .setRank(user.getRank());

        Response<UserVo> response = new Response<>("1001", userVo);

        return response;
    }
}
