package com.example.module.tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author XRS
 * @date 2024-08-29 下午 2:40
 */
public class ArTool {
    private ArTool() {
        throw new RuntimeException("此仅获取长宽比用");
    }

    public static float getAr(String imageSrc) {
        //正则
        // .*->(匹配如何字符)
        // \\.->(转义，匹配.)
        // \\d+->(匹配一次或多次数字并捕获)
        String arRegular = ".*_(\\d+)x(\\d+)\\..*";

        Pattern pattern = Pattern.compile(arRegular);
        Matcher matcher = pattern.matcher(imageSrc);

        float ar = 1;
        if (matcher.find()) {
            int width = Integer.parseInt(matcher.group(1));
            int height = Integer.parseInt(matcher.group(2));
            if (height != 0) {
                ar = width / height;
            }
        }

        return ar;
    }
}
