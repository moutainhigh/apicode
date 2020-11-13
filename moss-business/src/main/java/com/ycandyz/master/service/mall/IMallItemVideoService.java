package com.ycandyz.master.service.mall;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.entities.mall.MallItemVideo;
import com.ycandyz.master.exception.BusinessException;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * @Description 商品视频信息 业务接口类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-10
 * @version 2.0
 */
public interface IMallItemVideoService extends IService<MallItemVideo>{

    boolean insert(MallItemVideo entity, MultipartFile file, MultipartFile img);

    boolean update(MallItemVideo entity, MultipartFile file, MultipartFile img);
}
