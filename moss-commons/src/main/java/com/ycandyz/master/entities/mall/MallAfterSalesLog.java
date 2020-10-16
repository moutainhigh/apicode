package com.ycandyz.master.entities.mall;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@TableName("mall_after_sales_log")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="mall_after_sales_log对象", description="售后沟通日志表")
public class MallAfterSalesLog extends Model {

    @ApiModelProperty(value = "id")
    @TableId(type = IdType.AUTO)
    private Long id;
    /**订单编号*/
    @ApiModelProperty(value = "订单详情售后编号")
    private String afterSalesNo;
    /**订单编号*/
    @ApiModelProperty(value = "店铺编号")
    private String shopNo;
    /**订单编号*/
    @ApiModelProperty(value = "用户id")
    private Long userId;
    /**订单编号*/
    @ApiModelProperty(value = "订单详情售后沟通日志编号")
    private String logNo;
    /**订单编号*/
    @ApiModelProperty(value = "商家视角的内容")
    private String contextShop;
    /**订单编号*/
    @ApiModelProperty(value = "买家视角的内容")
    private String contextBuyer;
    /**订单编号*/
    @ApiModelProperty(value = "createdTime")
    private Date createdTime;
    /**订单编号*/
    @ApiModelProperty(value = "updatedTime")
    private Date updatedTime;
}
