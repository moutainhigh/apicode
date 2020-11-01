package com.ycandyz.master.domain.response.mall;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ycandyz.master.entities.mall.MallBuyerShipping;
import com.ycandyz.master.entities.mall.MallOrderDetail;
import com.ycandyz.master.entities.mall.MallShop;
import com.ycandyz.master.entities.mall.MallSkuSpec;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@TableName("mall_order_detail")
public class MallOrderDetailShopResp extends MallOrderDetail {

    @ApiModelProperty(value = "订单运费")
    private BigDecimal freightMoney;

    @ApiModelProperty(value = "总金额（包含real_money和shop_shipping中的运费）")
    private BigDecimal allMoney;

    @ApiModelProperty(value = "店铺信息")
    private MallShop mallShop;

    @ApiModelProperty(value = "商品规格Spec")
    private List<MallSkuSpec> mallSkuSpec;

    @ApiModelProperty(value = "快递信息")
    private MallBuyerShipping mallBuyerShipping;
}
