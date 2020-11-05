package com.ycandyz.master.domain.response.mall;

import com.ycandyz.master.entities.mall.MallShop;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 商城 - 店铺表 Resp
 * </p>
 *
 * @author SanGang
 * @since 2020-11-05
 * @version 2.0
 */
@Getter
@Setter
@TableName("mall_shop")
public class MallShopResp extends MallShop {

}
