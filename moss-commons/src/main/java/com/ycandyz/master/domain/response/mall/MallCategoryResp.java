package com.ycandyz.master.domain.response.mall;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * <p>
 * @Description 商城 - 商品分类表 数据表字段映射类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-15
 * @version 2.0
 */
@Getter
@Setter
@TableName("mall_category")
@ApiModel(description="商城 - 商品分类表")
public class MallCategoryResp {


   @TableId(value = "id", type = IdType.AUTO)
   private Long id;

   @ApiModelProperty(value = "商店编号")
   private String shopNo;

   @ApiModelProperty(value = "分类编号")
   private String categoryNo;

   @ApiModelProperty(value = "父分类编号")
   private String parentCategoryNo;

   @ApiModelProperty(value = "分类名称")
   private String categoryName;

   @ApiModelProperty(value = "分类图片")
   private String categoryImg;

   @ApiModelProperty(value = "分类处于第几级")
   private Integer layer;

   @ApiModelProperty(value = "排序值")
   private Integer sortValue;

   @ApiModelProperty(value = "状态：1: 正常，0: 无效  默认1")
   private Integer status;

   @ApiParam(hidden = true)
   @TableField(exist = false)
   @ApiModelProperty(value = "是否选中(true选择,false未选择)")
   private Boolean selected;

   @ApiModelProperty(value = "实体")
   private MallCategoryResp category;


}
