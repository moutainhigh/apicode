package com.ycandyz.master.domain.query.mall.uApp;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="订单 - 买家自提快递出货表")
public class MallPickupUAppQuery {

    @ApiModelProperty(value = "自提码", name = "pickup_no")
    @JsonAlias(value = {"pickup_no", "pickupNo"})
    private String pickupNo;
    @ApiModelProperty(value = "订单编号", name = "order_no")
    @JsonAlias(value = {"order_no", "orderNo"})
    private String orderNo;
}
