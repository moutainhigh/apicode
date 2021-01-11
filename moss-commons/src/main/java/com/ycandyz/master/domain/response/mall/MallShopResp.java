package com.ycandyz.master.domain.response.mall;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ycandyz.master.entities.mall.MallShop;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

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
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@TableName("mall_shop")
@ApiModel(description="商城 - 店铺表")
public class MallShopResp extends Model {


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(name = "organize_id",value = "企业id")
    private Integer organizeId;

    @ApiModelProperty(name = "shop_no",value = "商店编号")
    private String shopNo;

    @ApiModelProperty(name = "shop_name",value = "商店名称")
    private String shopName;

    @ApiModelProperty(name = "bg_img",value = "商店背景图")
    private String bgImg;

    @ApiModelProperty(name = "entry_name",value = "入口名称")
    private String entryName;

    @ApiModelProperty(value = "可售后天数")
    private Integer asHoldDays;

    @ApiModelProperty(value = "客服联系人")
    private String csContact;

    @ApiModelProperty(value = "客服电话号码")
    private String csPhoneNumber;

    @ApiModelProperty(value = "客服微信号")
    private String csWeixinNo;

    @ApiModelProperty(value = "商店状态 10: 正常  20：过期")
    private Integer status;

    private Date createdTime;

    private Date updatedTime;

    @ApiModelProperty(value = "商店经营状态 10:营业  20:休息 30:待启用")
    private Integer operateStatus;


}

