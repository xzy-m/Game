package com.example.module.tool;

import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author XRS
 * @date 2024-09-13 下午 5:07
 */
public class AccountTool {
    private AccountTool() {
        throw new RuntimeException("仅处理用户账号有关用");
    }

    public static String md5(String password) {
        String passwordMD5 = DigestUtils.md5Hex(password);
        return passwordMD5;
    }

    /**
     * 校验手机号码格式是否正确
     * @param phone 手机号码
     * @return 如果格式正确返回true，否则返回false
     */
    public static boolean isValidPhone(String phone) {
        // 定义手机号码的正则表达式
        // 此正则表达式匹配以13、14、15、17、18开头的11位数字
        String regex = "^(13|14|15|17|18)\\d{9}$";

        // 使用Pattern和Matcher类进行匹配
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }

        return phone.matches(regex);
    }

    public static String sha256Hex(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodeHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            //将byte转为hex格式
            StringBuilder builder = new StringBuilder(2 * encodeHash.length);
            for (int i = 0; i < encodeHash.length; i++) {
                String hexString = Integer.toHexString(0xff & encodeHash[i]);
                //拼接 0 或者 hexString 都是一句话，省略{}
                if (hexString.length() == 1) builder.append('0');
                builder.append(hexString);
            }

            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("加密工具出错");
        }
    }
}

