package com.example.app.controller;

import com.alibaba.excel.EasyExcel;
import com.example.app.excel.ExcelListener;
import com.example.module.entity.Game;
import com.example.module.response.Response;
import com.example.module.service.GameService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author XRS
 * @date 2024-09-22 下午 4:33
 */
@RestController
public class ExcelController {

    @Autowired
    private ExcelListener excelListener;

    @Autowired
    private GameService gameService;

    //下载是读 读就需要返回
    @RequestMapping("/excel/read")
    public Response readExcel(HttpServletResponse response) {
        List<Game> games = gameService.gameAll();

        //设置响应头  响应头晚点搜下是什么
        String fileName = "游戏列表_" + (int) (System.currentTimeMillis()) + ".xlsx";
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");

        //encode抛异常
        String encodedFileName = null;
        try {
            encodedFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            return new Response("1004", "文件编码为安全格式出错");
        }
        response.setHeader("Content-Disposition", "attachment;filename*=UTF-8''" + encodedFileName);

        //getOutputStream抛异常
        try {
            EasyExcel.write(response.getOutputStream(), Game.class).sheet("游戏信息").doWrite(games);
            return new Response("1001");
        } catch (IOException e) {
            return new Response("1004", "EasyExcel获取OutputStream出错");
        }
    }

    //上传是写
    @RequestMapping("/excel/write")
    public Response writeExcel(@RequestParam("file") MultipartFile file) {
        if (null == file) {
            return new Response("1003");
        }

        try {
            List<Game> sync = EasyExcel.read(file.getInputStream(), Game.class, excelListener).sheet().doReadSync();
            return new Response("1001", sync);
        } catch (IOException e) {
            return new Response("1004", "EasyExcel获取InputStream出错");
        }
    }
}
