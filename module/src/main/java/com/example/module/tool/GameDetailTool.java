package com.example.module.tool;

/**
 * @author XRS
 * @date 2024-09-26 下午 2:41
 */

//如果打算自定义自己的方法，那么必须在enum实例序列的最后添加一个分号。而且 Java 要求必须先定义 enum 实例。
public enum GameDetailTool {
    Text(1, "text"), Image(2, "image"), Video(3, "video");

    private int number;
    private String type;

    GameDetailTool(int number, String type) {
        this.number = number;
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }

    public static boolean checkDetail(String type) {
        if (Text.getType().equals(type)) {
            return true;
        }
        if (Image.getType().equals(type)) {
            return true;
        }
        if (Video.getType().equals(type)) {
            return true;
        }
        return false;
    }
}
