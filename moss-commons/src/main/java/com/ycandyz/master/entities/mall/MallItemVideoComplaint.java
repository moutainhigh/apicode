package com.ycandyz.master.entities.mall;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 商品视频投诉 数据表字段映射类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-10
 * @version 2.0
 */
@Getter
@Setter
@TableName("mall_item_video_complaint")
@ApiModel(description="商品视频投诉")
public class MallItemVideoComplaint extends Model {


   @ApiModelProperty(value = "ID")
   private Long id;

   @ApiModelProperty(value = "视频投诉编号")
   private String complaintNo;

   @ApiModelProperty(value = "商品视频编号")
   private String videoNo;

   @ApiModelProperty(value = "投诉类型(预留)")
   private Integer type;

   @ApiModelProperty(value = "视频投诉内容")
   private String content;

   @ApiModelProperty(value = "视频投诉人(匿名投诉为0)")
   private Long userId;

   @ApiModelProperty(value = "投诉状态(0未处理,1已处理)")
   private Integer status;

   @ApiModelProperty(value = "删除标识(0未删除,1已删除)")
   private Integer deleeted;

   @ApiModelProperty(value = "投诉时间")
   private Date createdTime;

   @ApiModelProperty(value = "更新时间")
   private Date updatedTime;


}
