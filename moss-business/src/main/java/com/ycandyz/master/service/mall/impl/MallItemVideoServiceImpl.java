package com.ycandyz.master.service.mall.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ycandyz.master.constant.CommonConstant;
import com.ycandyz.master.constant.Config;
import com.ycandyz.master.constant.SymbolConstant;
import com.ycandyz.master.entities.mall.MallAfterSales;
import com.ycandyz.master.entities.mall.MallItem;
import com.ycandyz.master.entities.mall.MallItemVideo;
import com.ycandyz.master.domain.query.mall.MallItemVideoQuery;
import com.ycandyz.master.dao.mall.MallItemVideoDao;
import com.ycandyz.master.exception.BusinessException;
import com.ycandyz.master.service.mall.IMallItemService;
import com.ycandyz.master.service.mall.IMallItemVideoService;
import com.ycandyz.master.controller.base.BaseService;

import com.ycandyz.master.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Date;
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
    @Autowired
    private IMallItemService mallItemService;

    @Value("${video-local-path}")
    private String localPath;

    @Override
    public boolean insert(MallItemVideo entity, MultipartFile file) {
        entity.setVideoNo(String.valueOf(IDGeneratorUtils.getLongId()));
        String inputFile = file.getOriginalFilename();
        String suffix = inputFile.substring(inputFile.lastIndexOf(SymbolConstant.SYMBOL_1) + 1,inputFile.length()).toLowerCase();
        String videoName = entity.getVideoNo()+SymbolConstant.SYMBOL_1+suffix;
        String videoPath =localPath+ File.separator+videoName;
        //s3商店视频地址命名规则,商店编号+视频编号
        LambdaQueryWrapper<MallItem> wrapper = new LambdaQueryWrapper<MallItem>()
                .select(MallItem::getShopNo)
                .eq(MallItem::getItemNo,entity.getItemNo());
        MallItem mallItem = mallItemService.getOne(wrapper);
        String s3Suffix = SymbolConstant.SYMBOL_2+mallItem.getShopNo()+File.separator+videoName;
        try {
            FileUtil.writeFile(file.getInputStream(),new File(videoPath));
            String url = s3UploadFile.upload(new File(videoPath), s3Suffix);
            entity.setUrl(url);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            AssertUtils.notNull(null,"视频上传服务器失败!");
        }
        entity.setTitle(inputFile);
        entity.setSize(file.getSize());
        //删掉本地视频文件
        FileUtil.deleteFile(new File(videoPath));
        return super.save(entity);
    }
}
