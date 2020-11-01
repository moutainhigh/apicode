package com.ycandyz.master.entities.ad;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * <p>
 * @Description 专题-商品表 数据表字段映射类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-23
 * @version 2.0
 */
@Getter
@Setter
@TableName("ad_specail_item")
@ApiModel(description="专题-商品表")
public class SpecailItem extends Model {


   @ApiModelProperty(value = "ID")
   @TableId(value = "id", type = IdType.AUTO)
   private Long id;

   @ApiModelProperty(value = "商店编号")
   private String shopNo;

   @ApiModelProperty(value = "专题编号")
   private String specialNo;

   @ApiModelProperty(value = "商品编号")
   private String itemNo;

   @ApiModelProperty(value = "创建时间")
   private Date createdTime;

   @ApiModelProperty(value = "更新时间")
   private Date updatedTime;


}
