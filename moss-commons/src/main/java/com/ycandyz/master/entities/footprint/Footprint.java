package com.ycandyz.master.entities.footprint;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 商友圈表 数据表字段映射类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-27
 * @version 2.0
 */
@Getter
@Setter
@TableName("footprint")
@ApiModel(description="商友圈表")
public class Footprint extends Model {


   @ApiModelProperty(value = "主键")
   @TableId(value = "id", type = IdType.AUTO)
   private Long id;

   @ApiModelProperty(value = "用户id")
   private Long userId;

   @ApiModelProperty(value = "内容")
   private String content;

   @ApiModelProperty(value = "阅读量")
   private Integer readNum;

   @ApiModelProperty(value = "经度")
   private String longitude;

   @ApiModelProperty(value = "纬度")
   private String latitude;

   @ApiModelProperty(value = "是否删除 0-否 1-是")
   private Boolean isDel;

   @ApiModelProperty(value = "创建时间")
   private Integer createdAt;

   @ApiModelProperty(value = "更新时间")
   private Integer updatedAt;


}
