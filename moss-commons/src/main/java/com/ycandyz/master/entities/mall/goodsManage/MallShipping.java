package com.ycandyz.master.entities.mall.goodsManage;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Description: 运费规格表
 * @Author li Zhuo
 * @Date 2020/10/9
 * @Version: V1.0
*/
@Data
@TableName("mall_shipping")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="mall_shipping对象", description="运费规格表")
public class MallShipping {

    @TableId(type = IdType.UUID)
    @ApiModelProperty(value = "id")
    private long id;
    @ApiModelProperty(value = "商店编号")
    private String shopNo;
    @ApiModelProperty(value = "运费模版编号")
    private String shippingNo;
    @ApiModelProperty(value = "运费模版名称")
    private String shippingName;
    @ApiModelProperty(value = "运费方式, 1: 按件")
    private Integer shippingMethod;
    @ApiModelProperty(value = "排序值")
    private Integer sortValue;
    @ApiModelProperty(value = "状态：1: 正常，0: 无效  默认1")
    private Integer status;
    @ApiModelProperty(value = "createdTime")
    private Date createdTime;
    @ApiModelProperty(value = "updatedTime")
    private Date updatedTime;
}
