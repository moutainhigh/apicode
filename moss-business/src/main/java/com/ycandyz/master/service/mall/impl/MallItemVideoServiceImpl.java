package com.ycandyz.master.service.mall.impl;

import com.ycandyz.master.constant.SymbolConstant;
import com.ycandyz.master.constant.VideoConstant;
import com.ycandyz.master.domain.enums.mall.MallItemVideoEnum;
import com.ycandyz.master.entities.mall.MallItemVideo;
import com.ycandyz.master.domain.query.mall.MallItemVideoQuery;
import com.ycandyz.master.dao.mall.MallItemVideoDao;
import com.ycandyz.master.service.mall.IMallItemVideoService;
import com.ycandyz.master.controller.base.BaseService;

import com.ycandyz.master.utils.*;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.UUID;

/**
 * <p>
 * @Description 商品视频信息 业务类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-10
 * @version 2.0
 */
@Slf4j
@Service
public class MallItemVideoServiceImpl extends BaseService<MallItemVideoDao,MallItemVideo,MallItemVideoQuery> implements IMallItemVideoService {

    @Autowired
    private S3UploadFile s3UploadFile;

    @Value("${video-local-path}")
    private String localPath;

    @Override
    public boolean insert(MallItemVideo entity, MultipartFile video, MultipartFile img) {
        entity.setVideoNo(String.valueOf(IDGeneratorUtils.getLongId()));
        //视频
        String videoTitle = video.getOriginalFilename();
        String videoSuffix = videoTitle.substring(videoTitle.lastIndexOf(SymbolConstant.SYMBOL_1) + 1,videoTitle.length()).toLowerCase();
        AssertUtils.isTrue(VideoConstant.FORMAT_MP4.equals(videoSuffix), "暂不支持此格式视频!");
        String uuid = UUID.randomUUID().toString().replace(SymbolConstant.SYMBOL_3, SymbolConstant.SYMBOL_4);
        String videoName = uuid+SymbolConstant.SYMBOL_1+videoSuffix;
        String videoPath =localPath+videoName;
        //s3商店视频地址命名规则,商店编号+视频编号
        AssertUtils.notNull(getShopNo(),"商品编号不正确");
        String s3Suffix = getShopNo()+File.separator+videoName;
        entity.setShopNo(getShopNo());
        entity.setTitle(videoTitle);
        entity.setSize(video.getSize());
        getVideoInfo(videoPath,entity);
        try {
            FileUtil.uploadFile(localPath,videoName,video.getInputStream());
            String url = s3UploadFile.upload(new File(videoPath), s3Suffix);
            entity.setUrl(url);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            AssertUtils.notNull(null,"视频上传服务器失败!");
        }finally {
            FileUtil.deleteFile(new File(videoPath));
        }
        //图片
        if(null != img){
            String imgTitle = img.getOriginalFilename();
            String imgSuffix = imgTitle.substring(imgTitle.lastIndexOf(SymbolConstant.SYMBOL_1) + 1,imgTitle.length()).toLowerCase();
            String imgName = uuid+SymbolConstant.SYMBOL_5+imgSuffix;
            String imgPath =localPath+imgName;
            //s3商店视频地址命名规则,商店编号+视频编号
            String s3ImgSuffix = getShopNo()+File.separator+imgName;
            entity.setShopNo(getShopNo());
            getVideoInfo(videoPath,entity);
            try {
                FileUtil.uploadFile(localPath,imgName,img.getInputStream());
                String url = s3UploadFile.upload(new File(imgPath), s3ImgSuffix);
                entity.setImg(url);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
                AssertUtils.notNull(null,"视频缩略图上传服务器失败!");
            }finally {
                FileUtil.deleteFile(new File(imgPath));
            }
        }
        return super.save(entity);
    }

    @Override
    public boolean update(MallItemVideo entity, MultipartFile video, MultipartFile img) {
        String uuid = UUID.randomUUID().toString().replace(SymbolConstant.SYMBOL_3, SymbolConstant.SYMBOL_4);
        //视频
        if(null != video){
            String videoTitle = video.getOriginalFilename();
            String videoSuffix = videoTitle.substring(videoTitle.lastIndexOf(SymbolConstant.SYMBOL_1) + 1,videoTitle.length()).toLowerCase();
            AssertUtils.isTrue(VideoConstant.FORMAT_MP4.equals(videoSuffix), "暂不支持此格式视频!");
            String videoName = uuid+SymbolConstant.SYMBOL_1+videoSuffix;
            String videoPath =localPath+videoName;
            //s3商店视频地址命名规则,商店编号+视频编号
            AssertUtils.notNull(getShopNo(),"商品编号不正确");
            String s3Suffix = getShopNo()+File.separator+videoName;
            entity.setTitle(videoTitle);
            entity.setSize(video.getSize());
            getVideoInfo(videoPath,entity);
            try {
                FileUtil.uploadFile(localPath,videoName,video.getInputStream());
                String url = s3UploadFile.upload(new File(videoPath), s3Suffix);
                entity.setUrl(url);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
                AssertUtils.notNull(null,"视频上传服务器失败!");
            }finally {
                FileUtil.deleteFile(new File(videoPath));
            }
        }
        //图片
        if(null != img){
            String imgTitle = img.getOriginalFilename();
            String imgSuffix = imgTitle.substring(imgTitle.lastIndexOf(SymbolConstant.SYMBOL_1) + 1,imgTitle.length()).toLowerCase();
            String imgName = uuid+SymbolConstant.SYMBOL_5+imgSuffix;
            String imgPath =localPath+imgName;
            //s3商店视频地址命名规则,商店编号+视频编号
            String s3ImgSuffix = getShopNo()+File.separator+imgName;
            try {
                FileUtil.uploadFile(localPath,imgName,img.getInputStream());
                String url = s3UploadFile.upload(new File(imgPath), s3ImgSuffix);
                entity.setImg(url);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
                AssertUtils.notNull(null,"视频缩略图上传服务器失败!");
            }finally {
                FileUtil.deleteFile(new File(imgPath));
            }
        }
        //删掉本地视频文件
        return true;
    }

    private void getVideoInfo(String filePath,MallItemVideo model){
        File source = new File(filePath);
        Encoder encoder = new Encoder();
        FileChannel fc= null;
        try {
            MultimediaInfo m = encoder.getInfo(source);
            Long ls = m.getDuration()/1000;
            model.setDuration(ls.intValue());
            int fps = (int)Math.ceil(m.getVideo().getFrameRate());
            model.setFps(fps+"/1");
            model.setRate(m.getVideo().getBitRate());
            model.setCodec(m.getVideo().getDecoder());
            model.setWidth(m.getVideo().getSize().getWidth());
            model.setHeight(m.getVideo().getSize().getHeight());
            model.setFormat(m.getFormat());
            FileInputStream fis = new FileInputStream(source);
            fc= fis.getChannel();
            model.setSize(fc.size());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (null!=fc){
                try {
                    fc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
