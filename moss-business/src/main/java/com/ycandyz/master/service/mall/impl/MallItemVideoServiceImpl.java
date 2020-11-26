package com.ycandyz.master.service.mall.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.constant.SymbolConstant;
import com.ycandyz.master.constant.VideoConstant;
import com.ycandyz.master.domain.enums.mall.MallItemVideoEnum;
import com.ycandyz.master.domain.query.base.BaseBankQuery;
import com.ycandyz.master.entities.base.BaseBank;
import com.ycandyz.master.entities.mall.MallItemVideo;
import com.ycandyz.master.domain.query.mall.MallItemVideoQuery;
import com.ycandyz.master.dao.mall.MallItemVideoDao;
import com.ycandyz.master.exception.BusinessException;
import com.ycandyz.master.service.mall.IMallItemVideoService;
import com.ycandyz.master.controller.base.BaseService;

import com.ycandyz.master.utils.*;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;
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
    public boolean upload(MallItemVideo entity, MultipartFile video, MultipartFile img) {
        //校验,如果是详情视频,必须上传缩略图
        AssertUtils.notNull(video,"视频文件不能为空");
        AssertUtils.notNull(getShopNo(),"商品编号不正确");
        //视频
        String videoTitle = video.getOriginalFilename();
        String videoSuffix = videoTitle.substring(videoTitle.lastIndexOf(SymbolConstant.SYMBOL_1) + 1,videoTitle.length()).toLowerCase();
        AssertUtils.isTrue(VideoConstant.FORMAT_MP4.equals(videoSuffix), "暂不支持此格式视频!");
        String uuid = UUID.randomUUID().toString().replace(SymbolConstant.SYMBOL_3, SymbolConstant.SYMBOL_4);
        String videoName = uuid+SymbolConstant.SYMBOL_1+videoSuffix;
        String videoPath =localPath+videoName;
        //s3商店视频地址命名规则,商店编号+视频编号
        String s3Suffix = getShopNo()+File.separator+videoName;
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
        return true;
    }

    @Override
    public Page<MallItemVideo> page(Page page, MallItemVideoQuery query) {
        LambdaQueryWrapper<MallItemVideo> queryWrapper = new LambdaQueryWrapper<MallItemVideo>()
                .select(MallItemVideo::getId,MallItemVideo::getType,MallItemVideo::getUrl,MallItemVideo::getImg)
                .eq(StrUtil.isNotEmpty(query.getItemNo()),MallItemVideo::getItemNo, query.getItemNo())
                .orderByDesc(MallItemVideo::getCreatedTime);
        return (Page<MallItemVideo>) baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public List<MallItemVideo> list(MallItemVideoQuery query) {
        AssertUtils.notNull(query.getItemNo(),"商品编号不能为空");
        LambdaQueryWrapper<MallItemVideo> queryWrapper = new LambdaQueryWrapper<MallItemVideo>()
                .select(MallItemVideo::getId,MallItemVideo::getType,MallItemVideo::getUrl,MallItemVideo::getImg)
                .eq(StrUtil.isNotEmpty(query.getItemNo()),MallItemVideo::getItemNo, query.getItemNo())
                .orderByDesc(MallItemVideo::getCreatedTime);
        return baseMapper.selectList(queryWrapper);
    }


    @Override
    public boolean audit(Long id, Integer status, String remark) {
        /*
        MallItemVideoEnum.Check s = MallItemVideoEnum.Check.parseCode(status);
        AssertUtils.notNull(s,"审核状态不正确");
        MallItemVideo itemVideo = baseMapper.selectById(id);
        AssertUtils.notNull(itemVideo,"该视频不存在");
        MallItemVideoEnum.Audit audit = MallItemVideoEnum.Audit.parseCode(itemVideo.getAudit());
        AssertUtils.isFalse(audit.getCode().equals(MallItemVideoEnum.Audit.START_2.getCode()),"审核状态为拒绝状态,不支持此操作!");
        MallItemVideoEnum.Status st = MallItemVideoEnum.Status.parseCode(itemVideo.getStatus());
        if(st.getCode().equals(MallItemVideoEnum.Status.START_0.getCode())){
            if(s.getCode().equals(MallItemVideoEnum.Check.CHECK_0.getCode())){
                itemVideo.setAudit(MallItemVideoEnum.Audit.START_1.getCode());
            }else {
                itemVideo.setAudit(MallItemVideoEnum.Audit.START_2.getCode());
            }
        }else {
            itemVideo.setAudit(MallItemVideoEnum.Audit.START_1.getCode());
            if(s.getCode().equals(MallItemVideoEnum.Check.CHECK_0.getCode())){
                itemVideo.setStatus(MallItemVideoEnum.Status.START_1.getCode());
            }else {
                itemVideo.setStatus(MallItemVideoEnum.Status.START_3.getCode());
            }
        }
        itemVideo.setRemark(remark);
        return super.updateById(itemVideo);
        */
        return false;
    }

    /*private void getVideoInfo(String filePath,MallItemVideo model){
        File source = new File(filePath);
        Encoder encoder = new Encoder();
        FileChannel fc= null;
        try {
            MultimediaInfo m = encoder.getInfo(source);
            FileInputStream fis = new FileInputStream(source);
            fc= fis.getChannel();
            Long ls = m.getDuration()/1000;
            int fps = (int)Math.ceil(m.getVideo().getFrameRate());
            model.setDuration(ls.intValue());
            model.setFps(fps+"/1");
            model.setRate(m.getVideo().getBitRate());
            model.setCodec(m.getVideo().getDecoder());
            model.setWidth(m.getVideo().getSize().getWidth());
            model.setHeight(m.getVideo().getSize().getHeight());
            model.setFormat(m.getFormat());
            model.setSize(fc.size());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            throw new BusinessException("视频格式错误");
        }finally {
            if (null!=fc){
                try {
                    fc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/
}
