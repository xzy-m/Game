package com.example.app.tool;

import com.alibaba.fastjson.JSON;
import com.example.module.response.Response;
import com.example.module.tool.SignTool;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.PrintWriter;




//需要一个配置类把GameInterceptor注册到SpringMVC中心  在那里指定什么请求的前后会执行下面三个方法
@Component
@Slf4j
public class GameInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        //String sessionSign = request.getParameter("sign");

        //在请求controller处理之前调用
        Cookie[] cookies = request.getCookies();
        String cookieSign = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("sign".equals(cookie.getName())) {
                    cookieSign = cookie.getValue();
                }
            }
        }

        boolean result;
        PrintWriter writer = null;
        try {
            // 设置响应的内容类型
            response.setContentType("application/json");
            // 获取PrintWriter
            writer = response.getWriter();
            // 验证签名
            result = SignTool.checkSign(cookieSign);

            if (!result) {
                // 2002未登录
                Response<String> errorResponse = new Response<>("2002");
                String errorString = JSON.toJSONString(errorResponse);

                // 写入错误信息
                writer.println(errorString);
                writer.flush();

                return false;
            }
        } catch (Exception e) {
            // 有异常
            Response<String> errorResponse = new Response<>("1004");
            String errorString = JSON.toJSONString(errorResponse);

            // 如果已经获取了PrintWriter，则使用它写入错误信息
            writer.println(errorString);
            writer.flush();
            return false;
        }

        // 如果签名验证成功，则返回true
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        //请求处理（controller方法调用）之后进行调用，但是在视图被渲染之前
        System.out.println("controller方法被调用了，视图还没渲染");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //在整个请求结束之后调用，也就是在DispatcherServlet渲染了视图执行？？？
        System.out.println("渲染视图已完成");
    }
}
