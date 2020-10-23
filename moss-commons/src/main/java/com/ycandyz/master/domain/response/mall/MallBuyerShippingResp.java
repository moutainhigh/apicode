package com.ycandyz.master.domain.response.mall;

import com.ycandyz.master.entities.mall.MallBuyerShipping;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 买家寄出的快递表 Resp
 * </p>
 *
 * @author SanGang
 * @since 2020-10-23
 * @version 2.0
 */
@Getter
@Setter
@TableName("mall_buyer_shipping")
public class MallBuyerShippingResp extends MallBuyerShipping {

}
