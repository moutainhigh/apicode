package com.ycandyz.master.domain.model.mall;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
 import lombok.Data;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.io.Serializable;

/**
 * <p>
 * @Description 商城 - 店铺表 Model
 * </p>
 *
 * @author SanGang
 * @since 2021-01-06
 * @version 2.0
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description="商城 - 店铺表-Model")
public class MallShopModel implements Serializable {

   private static final long serialVersionUID = 1L;

   private Long id;

   @ApiModelProperty(name = "organize_id", value = "企业id")
   private Integer organizeId;

   @ApiModelProperty(name = "shop_no", value = "商店编号")
   private String shopNo;

   @ApiModelProperty(name = "shop_name", value = "商店名称")
   private String shopName;

   @ApiModelProperty(name = "bg_img", value = "商店背景图")
   private String bgImg;

   @ApiModelProperty(name = "entry_name", value = "入口名称")
   private String entryName;

   @ApiModelProperty(name = "as_hold_days", value = "可售后天数")
   private Integer asHoldDays;

   @ApiModelProperty(name = "cs_contact", value = "客服联系人")
   private String csContact;

   @ApiModelProperty(name = "cs_phone_number", value = "客服电话号码")
   private String csPhoneNumber;

   @ApiModelProperty(name = "cs_weixin_no", value = "客服微信号")
   private String csWeixinNo;

   @ApiModelProperty(name = "status", value = "商店状态 10: 正常  20：过期")
   private Integer status;

   private Date createdTime;

   private Date updatedTime;

   @ApiModelProperty(name = "operate_status", value = "商店经营状态 10:营业  20:休息 30:待启用")
   private Integer operateStatus;

   @ApiModelProperty(name = "wx_mini_no", value = "小程序编号")
   private String wxMiniNo;

   @ApiModelProperty(name = "payment_channels", value = "支付渠道 1微信 2通联")
   private Boolean paymentChannels;

   @ApiModelProperty(name = "wx_mini_appid", value = "微信小程序appid")
   private String wxMiniAppid;

   @ApiModelProperty(name = "payee_account_type", value = "收款账户类型 1:有传账户 2:独立商户自有账户")
   private Integer payeeAccountType;


}

