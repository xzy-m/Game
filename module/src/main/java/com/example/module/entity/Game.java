package com.example.module.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigInteger;

/**
 * @author XRS
 * @date 2024-07-12 下午 3:44
 */
@Data
public class Game {
    //不用基础类型和数组
    @ExcelProperty("ID")
    private BigInteger id;
    @ExcelProperty("游戏图片")
    private String pictures;
    @ExcelProperty("游戏名称")
    private String title;
    @ExcelProperty("游戏下载链接")
    private String downloadLink;
    @ExcelProperty("创建时间")
    private Integer createTime;
    @ExcelProperty("修改时间")
    private Integer updateTime;
    @ExcelProperty("是否可读")
    private Integer isDeleted;
    @ExcelProperty("游戏类别")
    private BigInteger categoryId;
    //游戏的介绍  这一个字段存的JSON，包含了类型，内容（文本/视频）
    @ExcelProperty("游戏详情")
    private String detail;
    @ExcelProperty("标签集合")
    private String tags;
}
