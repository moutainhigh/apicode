package com.ycandyz.master.utils;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.URL;

@Component
@Slf4j
public class S3UploadFile {

    @Value("${s3.accessKey}")
    private String accessKey;

    @Value("${s3.secretKey}")
    private String secretKey;

    @Value("${s3.downFileUrlPefix}")
    private String downFileUrlPefix;

    @Value("${s3.bucketName}")
    private String bucketName;

    @Value("${s3.keyPefix}")
    private String keyPefix;

    private  BasicAWSCredentials awsCreds;
    /**
     * 创建s3对象
     */
    private  AmazonS3 s3;

    /**
     * 上传文件示例
     */
    public  String uploadFile(File file,String uploadPath) {
        try {
            if (file == null) {
                return "上传文件不存在";
            }
            String key = keyPefix + uploadPath;
            // 设置文件上传对象
            PutObjectRequest request = new PutObjectRequest(bucketName, key, file);
            // 上传文件
            s3.putObject(request);
            //下载地址
            String url = downFileUrlPefix + key;
            log.info("下载地址:{}",url);
            return url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostConstruct
    public void init(){
        //初始化访问凭证
        this.awsCreds = new BasicAWSCredentials(accessKey, secretKey);
        //初始化s3对象
        this.s3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                //设置服务器所属地区
                .withRegion(Regions.CN_NORTH_1)
                .build();
    }
}
