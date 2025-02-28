package com.example.module.tool;

import com.alibaba.fastjson.JSON;
import com.example.module.wp.AppPageWP;

import java.util.Base64;

/**
 * @author XRS
 * @date 2024-08-21 上午 1:20
 */
public class WPTool {

    //构造函数private掉，里面抛异常
    private WPTool() {
        throw new RuntimeException("此工具不能new");
    }

    //编码：wp对象--wp的json字符串--wp的base64字符串--返回前端
    public static String encodeWP(AppPageWP appPageWP) {

        //对象转json
        String jsonString = JSON.toJSONString(appPageWP);

        //json转base64
        byte[] jsonBytes = jsonString.getBytes();
        String nestWPBase64 = Base64.getUrlEncoder().encodeToString(jsonBytes);

        return nestWPBase64;
    }

    //解码：拿到base64的wp字符串--wp的json字符串--json解码的wp对象--使用wp对象
    public static AppPageWP decodeWP(String wrapperParameter) {

        try {
            if (wrapperParameter == null) {
                return null;
            } else {
                //base64转json
                byte[] decode = Base64.getUrlDecoder().decode(wrapperParameter);
                String json = new String(decode);

                //json转为对象
                AppPageWP appPageWP = JSON.parseObject(json, AppPageWP.class);

                return appPageWP;
            }
        } catch (Exception e) {
            throw new RuntimeException("解码有误");
        }
    }
}
