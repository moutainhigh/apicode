package com.ycandyz.master.entities.mall;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * <p>
 * @Description sku值表 数据表字段映射类
 * </p>
 *
 * @author Wang Yang
 * @since 2020-10-19
 * @version 2.0
 */
@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@TableName("mall_sku_spec")
@ApiModel(description="sku值表")
public class MallSkuSpec extends Model {


   @TableId(value = "id", type = IdType.AUTO)
   private Long id;

   @ApiModelProperty(value = "sku编号")
   private String skuNo;

   @ApiModelProperty(value = "名称")
   private String specName;

   @ApiModelProperty(value = "值")
   private String specValue;

   @ApiModelProperty(value = "图片标识(0不存在,1存在)")
   private Integer existImg;

   @ApiModelProperty(value = "SKU图片（预留字段）")
   @TableField(exist = false)
   private String skuImg;

   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
   private Date createdTime;

   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
   private Date updatedTime;


}
