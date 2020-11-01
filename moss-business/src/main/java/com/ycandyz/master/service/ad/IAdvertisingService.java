package com.ycandyz.master.service.ad;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.domain.query.ad.AdvertisingQuery;
import com.ycandyz.master.entities.ad.Advertising;

import java.util.List;

/**
 * <p>
 * @Description 轮播图 业务接口类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-13
 * @version 2.0
 */
public interface IAdvertisingService extends IService<Advertising>{

    List<Advertising> selectHomeList(AdvertisingQuery query);
	
}
