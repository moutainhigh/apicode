package com.ycandyz.master.entities.mall;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("mall_temp_shipping")
@ApiModel(description="物流 - 未签收的临时物流表")
public class MallTempShipping extends Model {

    /**id*/
    @TableId(type = IdType.UUID)
    @ApiModelProperty(value = "id")
    private Long id;
    /**快递公司编码*/
    @ApiModelProperty(value = "快递公司编码")
    private String comCode;
    /**快递公司名*/
    @ApiModelProperty(value = "快递公司名")
    private String company;
    /**快递单号*/
    @ApiModelProperty(value = "快递单号")
    private String number;
    /**createdAt*/
    @ApiModelProperty(value = "createdAt")
    private Long createdAt;
}
