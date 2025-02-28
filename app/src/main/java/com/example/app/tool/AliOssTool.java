package com.example.app.tool;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyuncs.exceptions.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


@Component
@Slf4j
public class AliOssTool {

    private String bucketName = "fuckubucket";
    private String endpoint = "oss-cn-hangzhou.aliyuncs.com";
    private String accessKeyId = "LTAI5tGE64QuJCw6h5sJi7PQ";
    private String accessKeySecret = "Cn1jxI7uWM6IE10nVmHdmGwZTAXxoN";

    //本地保存图片路径
    private String path = "C:/Programming/Java/Game/app/src/main/resources/";


    public String uploadOss(MultipartFile file) {
        if (file == null) {
            return null;
        }

        //获得上传的文件的输入流
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            //throw new RuntimeException("上传的文件的输入流出错");
            log.info("上传的文件的输入流出错");
        }

        //要图片名
        String name = getNameForUpload(file);

        //上传文件到OSS
        OSS oss = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        oss.putObject(bucketName, name, inputStream);

        //上传之后可以访问文件的路径  完全复制百度  generatePresignedUrl中间是图片名
        Date expiration = new Date(new Date().getTime() + 3600 * 1000L);
        String imageUrl = oss.generatePresignedUrl(bucketName, name, expiration).toString();

        //关闭oss
        oss.shutdown();

        //imageUrl切掉？之后的
        int index = imageUrl.indexOf("?");
        imageUrl = imageUrl.substring(0, index);

        return imageUrl;
    }

    public void deleteOss(String objectName) {
        String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
        String bucketName = "fuckubucket";
        EnvironmentVariableCredentialsProvider credentialsProvider = null;
        try {
            credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
        } catch (ClientException e) {
            throw new RuntimeException("从环境变量中获取访问凭证失败");
        }
        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, credentialsProvider);

        // 删除文件
        ossClient.deleteObject(bucketName, objectName);
    }

    public String uploadLocal(MultipartFile multipartFile) {
        if (multipartFile == null) {
            return null;
        }

        //处理图片
        String name = getNameForUpload(multipartFile);

        //创建文件
        File file = new File(path, name);
        file.mkdirs();

        //复制图片给file,会有异常
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            throw new RuntimeException("复制图片步骤出错");
        }

        //返回文件绝对路径
        return file.getAbsolutePath();
    }

    private String getNameForUpload(MultipartFile multipartFile) {
        if (multipartFile == null) {
            return null;
        }

        //长宽比
        String resolution = null;
        try {
            BufferedImage image = ImageIO.read(multipartFile.getInputStream());
            if (image != null) {
                int width = image.getWidth();
                int height = image.getHeight();
                resolution = width + "x" + height;
            }
        } catch (IOException e) {
            throw new RuntimeException("获得图片长宽失败");
        }
        //日期
        String time = new SimpleDateFormat("yyyyMM/dd").format(new Date());
        //唯一标识
        String uuid = UUID.randomUUID().toString().replace("-", "");
        //两个方法作用依次是:得到图片名,最后一个点的位置
        int lasted = multipartFile.getOriginalFilename().lastIndexOf('.');
        //图片后缀
        String type = multipartFile.getOriginalFilename().substring(lasted);

        //image/202408/26/唯一文件名_100x200.后缀
        String name = "image/" + time + "/" + uuid + "_" + resolution + type;

        return name;
    }
}
