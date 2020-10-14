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
 * @Description  数据表字段映射类
 * </p>
 *
 * @author Wang Yang
 * @since 2020-10-13
 * @version 2.0
 */
@Getter
@Setter
@TableName("mall_self_pick_up_address")
@ApiModel(description="")
public class MallSelfPickUpAddress extends Model {


   @TableId(value = "id", type = IdType.AUTO)
   private Integer id;

   @ApiModelProperty(value = "地址名称")
   private String name;

   @ApiModelProperty(value = "省份信息")
   private String province;

   @ApiModelProperty(value = "省份id")
   private Integer provinceId;

   @ApiModelProperty(value = "城市信息")
   private String city;

   @ApiModelProperty(value = "城市id")
   private Integer cityId;

   @ApiModelProperty(value = "地区信息")
   private String district;

   @ApiModelProperty(value = "地区id")
   private Integer districtId;

   @ApiModelProperty(value = "店铺编号")
   private String shopNo;

   @ApiModelProperty(value = "是否删除 0-否 1-删除")
   private Boolean isDel;

   @ApiModelProperty(value = "详细地址信息")
   private String detailInfo;

   @ApiModelProperty(value = "是否默认 0-非默认 1-默认")
   private Boolean isDefault;

   @ApiModelProperty(value = "创建时间")
   private Date createdTime;

   @ApiModelProperty(value = "修改时间")
   private Date updatedTime;

   private Long createdAt;

   private Long updatedAt;


}
