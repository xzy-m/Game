package com.example.app.controller;

import com.example.module.tool.AliOssTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author XRS
 * @date 2024-08-28 下午 5:41
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    public AliOssTool aliOssTool;

    @PostMapping("/upload_local")
    public String uploadLocal(@RequestParam("multipartFile") MultipartFile multipartFile) {
        if (multipartFile == null) {
            return null;
        }
        try {
            return aliOssTool.uploadLocal(multipartFile);
        } catch (Exception e) {
            return "上传失败";
        }
    }

    @PostMapping("/upload_oss")
    public String uploadOss(@RequestParam("multipartFile") MultipartFile multipartFile) {
        if (multipartFile == null) {
            return null;
        }
        return aliOssTool.uploadOss(multipartFile);
    }
}
