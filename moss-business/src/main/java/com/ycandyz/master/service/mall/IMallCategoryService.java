package com.ycandyz.master.service.mall;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.entities.mall.MallCategory;

import java.util.List;

/**
 * <p>
 * @Description 商城 - 商品分类表 业务接口类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-15
 * @version 2.0
 */
public interface IMallCategoryService extends IService<MallCategory>{

    List<MallCategory> selectByLayerShopNo(String shopNo,String parentCategoryNo);

    List<MallCategory> selectByShopNo(String shopNo);
	
}
