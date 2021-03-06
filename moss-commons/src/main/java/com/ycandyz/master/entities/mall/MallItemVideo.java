package com.ycandyz.master.entities.mall;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ycandyz.master.validation.ValidatorContract;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * <p>
 * @Description 商品视频信息 数据表字段映射类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-10
 * @version 2.0
 */
@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@TableName("mall_item_video")
@ApiModel(description="商品视频信息")
public class MallItemVideo extends Model {


   @ApiModelProperty(value = "ID")
   @TableId(value = "id", type = IdType.AUTO)
   @ApiParam(hidden = true)
   private Long id;

   @ApiParam(hidden = true)
   @ApiModelProperty(value = "视频编号")
   private String videoNo;

   @ApiParam(hidden = true)
   @ApiModelProperty(value = "商品编号")
   @Size(max = 64, message = "商品编号长度不能大于64。",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
   private String itemNo;

   @ApiModelProperty(value = "视频栏目(0置顶视频,1描述视频)")
   @NotNull(message = "视频栏目不能为空",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
   @Range(min = 0, max = 1, message = "视频栏目类型不正确",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
   private Integer type;

   @ApiParam(hidden = true)
   @ApiModelProperty(value = "视频播放地址")
   private String url;

   @ApiParam(hidden = true)
   @ApiModelProperty(value = "视频缩略图")
   private String img;

   @JsonIgnore
   @ApiParam(hidden = true)
   @ApiModelProperty(value = "删除标识(0未删除,1已删除)")
   @TableLogic
   private Integer isDel;

   @ApiParam(hidden = true)
   @ApiModelProperty(value = "创建时间")
   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
   private Date createdTime;

   @ApiParam(hidden = true)
   @ApiModelProperty(value = "更新时间")
   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
   private Date updatedTime;


}
