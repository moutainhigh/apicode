package com.ycandyz.master.entities.mall;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

/**
 * <p>
 * @Description 商品-集团 数据表字段映射类
 * </p>
 *
 * @author SanGang
 * @since 2021-01-05
 * @version 2.0
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@TableName("mall_item_organize")
@ApiModel(description="商品-集团")
public class MallItemOrganize extends Model {

   @ApiModelProperty(name = "id", value = "ID")
   @TableId(value = "id", type = IdType.AUTO)
   private Long id;

   @ApiModelProperty(name = "shop_no", value = "店铺编号")
   private String shopNo;

   @ApiModelProperty(name = "organize_item_no", value = "集团商品编号")
   private String organizeItemNo;

   @ApiModelProperty(name = "item_no", value = "商品编号")
   private String itemNo;

   @ApiModelProperty(name = "category_no", value = "集团商品分类编号")
   private String categoryNo;

   @ApiModelProperty(name = "is_copy", value = "是否店铺自己数据(0:不是,1:是)")
   private Integer isCopy;

   @ApiModelProperty(name = "is_del", value = "删除状态(0:未删除,1:删除)")
   private Integer isDel;

   @ApiModelProperty(name = "create_time", value = "创建时间")
   private Date createTime;

   @ApiModelProperty(name = "update_time", value = "更新时间")
   private Date updateTime;


}
