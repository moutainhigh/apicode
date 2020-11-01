package com.ycandyz.master.entities.mall;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@TableName("mall_order_detail_spec")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="mall_order_detail_spec对象", description="订单详情规格值表")
public class MallOrderDetailSpec extends Model {

    @ApiModelProperty(value = "id")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "商品详情编号")
    private String orderDetailNo;
    @ApiModelProperty(value = "规格名称")
    private String specName;
    @ApiModelProperty(value = "规格值")
    private String specValue;
    @ApiModelProperty(value = "createdTime")
    private Date createdTime;
    @ApiModelProperty(value = "updatedTime")
    private Date updatedTime;
}
