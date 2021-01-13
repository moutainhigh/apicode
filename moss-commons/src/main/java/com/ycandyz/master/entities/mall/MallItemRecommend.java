package com.ycandyz.master.entities.mall;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Description 商品推荐基础表 数据表字段映射类
 * @author WangWx
 * @since 2021-01-12
 * @version 2.0
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@TableName("mall_item_recommend")
@ApiModel(description="商品推荐基础表")
public class MallItemRecommend extends Model {

   @TableId(value = "id", type = IdType.AUTO)
   private Long id;

   @ApiModelProperty(name = "item_recommend_no", value = "推荐编号")
   private String itemRecommendNo;

   @ApiModelProperty(name = "location_name", value = "推荐位置名称")
   private String locationName;

   @ApiModelProperty(name = "is_recommend", value = "默认是否推荐 0不推荐 1推荐")
   private Integer isRecommend;

   @ApiModelProperty(name = "recommend_way", value = "默认推荐方式 1自动推荐 2手动选择")
   private Integer recommendWay;

   @ApiModelProperty(name = "recommend_type", value = "自动推荐类型 1 最新添加的商品 2 全部商品浏览TOP 3 全部商品销售TOP 4 全部商品排序值TOP")
   private Integer recommendType;

   @ApiModelProperty(name = "show_num", value = "展示的数量")
   private Integer showNum;

   @ApiModelProperty(name = "status", value = "0-无效   1-有效")
   private Integer status;

   @ApiModelProperty(name = "created_time", value = "创建时间")
   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
   private Date createdTime;

   @ApiModelProperty(name = "updated_time", value = "修改时间")
   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
   private Date updatedTime;


}
