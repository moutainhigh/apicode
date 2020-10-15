package com.ycandyz.master.entities.mall;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 商城 - 店铺表 数据表字段映射类
 * </p>
 *
 * @author Wang Yang
 * @since 2020-10-13
 * @version 2.0
 */
@Getter
@Setter
@TableName("mall_shop")
@ApiModel(description="商城 - 店铺表")
public class MallShop extends Model {


   @TableId(value = "id", type = IdType.AUTO)
   private Long id;

   @ApiModelProperty(value = "企业id")
   private Integer organizeId;

   @ApiModelProperty(value = "商店编号")
   private String shopNo;

   @ApiModelProperty(value = "商店名称")
   private String shopName;

   @ApiModelProperty(value = "商店背景图")
   private String bgImg;

   @ApiModelProperty(value = "入口名称")
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
