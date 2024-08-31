package com.example.module.tool;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author XRS
 * @date 2024-08-31 下午 2:25
 */
public class ConvertSrcToMultipartFile {
    private ConvertSrcToMultipartFile() {
        throw new RuntimeException("仅用于将本地路径图片转为MultipartFile");
    }

    public static MultipartFile getMultipartFile(String src) {
        File image = new File(src);
        try {
            //1,什么勾八
            FileInputStream input = new FileInputStream(image);
            //2,MockMultipartFile 你也要catch？
            MockMultipartFile multipartFile = new MockMultipartFile("image", image.getName(), "text/plain", input);

            return multipartFile;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("本地图片路径转字节形式出错");
        } catch (IOException e) {
            throw new RuntimeException("这个catch是MockMultipartFile的");
        }
    }
}
