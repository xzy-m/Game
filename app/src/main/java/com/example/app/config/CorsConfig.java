package com.example.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                //这个源字 就是 域名
                .allowedOrigins("http://example.com", "http://a.com", "http://b.com", "http://c.com")
                //允许的方法  方法还挺多
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD")
                //允许发送cookie
                .allowCredentials(true)
                //预检请求的缓存时间
                .maxAge(3600)
                //允许的头信息
                .allowedHeaders("*")
                //暴露给前端的头部信息
                .exposedHeaders("Content-Disposition", "X-Suggested-Filename");
    }
}
