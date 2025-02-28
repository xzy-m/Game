package com.example.app.controller;

import com.example.module.response.Response;
import com.example.app.tool.AliOssTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;import org.springframework.web.multipart.MultipartFile;



@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    public AliOssTool aliOssTool;

    @RequestMapping("/local/image")
    public Response uploadLocal(@RequestParam("image") MultipartFile image) {
        try {
            String msg = aliOssTool.uploadLocal(image);
            Response<String> response = new Response<>("1001");
            response.setData(msg);
            return response;
        } catch (Exception e) {
            return new Response("1003");
        }
    }

    @RequestMapping("/oss/image")
    public Response uploadOss(@RequestParam("image") MultipartFile image) {
        String msg = aliOssTool.uploadOss(image);
        Response response = new Response("1001");
        response.setData(msg);
        return response;
    }
}
