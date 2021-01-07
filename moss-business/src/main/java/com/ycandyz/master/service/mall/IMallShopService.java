package com.ycandyz.master.service.mall;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.entities.mall.MallItem;
import com.ycandyz.master.entities.mall.MallShop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * @Description 商城 - 店铺表 业务接口类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-05
 * @version 2.0
 */
public interface IMallShopService extends IService<MallShop>{

    Page<MallShop> getByOrganizeId(Page<MallShop> page);

    Page<MallShop> getByItemNo(Page<MallShop> page, String itemNo);
	
}
