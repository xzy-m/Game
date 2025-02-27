package com.example.app.config;

import com.example.app.tool.GameInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;




//@Configuration  //这个注解意思是这个类是被用来初始化，初始化什么就不知道了，先不深究
public class GameConfig implements WebMvcConfigurer {

    @Autowired
    private GameInterceptor gameInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //将拦截器添加到注册中心
        registry.addInterceptor(gameInterceptor)
                //先拦截全部
                .addPathPatterns("/**")
                //配置不拦截的路径
                .excludePathPatterns("/app/user/login","/app/user/register","/excel/read","/excel/write");
    }
}
