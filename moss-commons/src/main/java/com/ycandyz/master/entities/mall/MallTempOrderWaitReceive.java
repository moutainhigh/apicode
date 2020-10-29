package com.ycandyz.master.entities.mall;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@TableName("mall_temp_order_wait_receive")
@ApiModel(description="物流 - 待收货的临时订单表")
public class MallTempOrderWaitReceive extends Model {

    /**id*/
    @TableId(type = IdType.UUID)
    @ApiModelProperty(value = "id")
    private Long id;
    /**临时订单编号*/
    @ApiModelProperty(value = "临时订单编号")
    private String tempOrderNo;
    /**订单编号*/
    @ApiModelProperty(value = "订单编号")
    private String orderNo;
    /**店铺编号*/
    @ApiModelProperty(value = "店铺编号")
    private String shopNo;
    /**店铺编号*/
    @ApiModelProperty(value = "订单将要关闭的时间")
    private Long closeAt;
    /**createdTime*/
    @ApiModelProperty(value = "createdTime")
    private Date createdTime;
    /**updatedTime*/
    @ApiModelProperty(value = "updatedTime")
    private Date updatedTime;
}
