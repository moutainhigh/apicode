package com.ycandyz.master.entities.mall;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ycandyz.master.validation.ValidatorContract;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
   @ApiParam(hidden = true)
   private Long id;

   @JsonIgnore
   @ApiParam(hidden = true)
   @ApiModelProperty(value = "视频投诉编号")
   private String complaintNo;

   @Size(max = 64, message = "视频编号长度不能大于64。",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
   @NotNull(message = "视频编号不能为空",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
   @ApiModelProperty(value = "商品视频编号")
   private String videoNo;

   @JsonIgnore
   @ApiParam(hidden = true)
   @ApiModelProperty(value = "投诉类型(预留)")
   private Integer type;

   @Size(max = 1000, message = "视频投诉内容长度不能大于1000。",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
   @ApiModelProperty(value = "视频投诉内容")
   private String content;

   @ApiParam(hidden = true)
   @ApiModelProperty(value = "视频投诉人(匿名投诉为0)")
   private Long userId;

   @ApiParam(hidden = true)
   @ApiModelProperty(value = "投诉状态(0未处理,1已处理)")
   private Integer status;

   @JsonIgnore
   @ApiParam(hidden = true)
   @ApiModelProperty(value = "删除标识(0未删除,1已删除)")
   @TableLogic
   private Integer deleeted;

   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
   @ApiModelProperty(value = "投诉时间")
   private Date createdTime;

   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
   @ApiModelProperty(value = "更新时间")
   private Date updatedTime;


}
