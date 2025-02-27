package com.example.module.response;

import java.util.HashMap;
import java.util.Map;

/**
 * @author XRS
 * @date 2024-09-12 下午 7:00
 */
public class ResponseStatusMap {
    private ResponseStatusMap() {
        throw new RuntimeException("此仅获取返回编码与信息map合集用");
    }

    private static Map<String, String> statusMap = new HashMap();

    static {
        statusMap.put("1001", "操作成功");
        statusMap.put("1002", "未登录");
        statusMap.put("1003", "操作失败");
        statusMap.put("1004", "出现异常，请稍后再试");
        statusMap.put("1005", "已注册账号");
        statusMap.put("1006", "游戏不存在");
        statusMap.put("4003", "权限不足");
        statusMap.put("4004", "网络繁忙");
        statusMap.put("2001", "账号不存在");
        statusMap.put("2002", "用户名或密码不匹配");
    }

    public static String getMSG(String code) {
        return statusMap.get(code);
    }
}
