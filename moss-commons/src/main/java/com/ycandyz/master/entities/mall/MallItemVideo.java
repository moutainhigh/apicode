package com.ycandyz.master.entities.mall;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
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
   @ApiModelProperty(value = "商店编号")
   @Size(max = 64, message = "商店编号长度不能大于64。",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
   private String shopNo;

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

   @ApiParam(hidden = true)
   @ApiModelProperty(value = "视频标题")
   private String title;

   @ApiParam(hidden = true)
   @ApiModelProperty(value = "视频的上传者")
   private Long userId;

   @ApiParam(hidden = true)
   @ApiModelProperty(value = "视频播放次数")
   private Long playCount;

   @ApiParam(hidden = true)
   @ApiModelProperty(value = "帧率")
   private String fps;

   @ApiParam(hidden = true)
   @ApiModelProperty(value = "视频时长")
   private Integer duration;

   @ApiParam(hidden = true)
   @ApiModelProperty(value = "大小")
   private Long size;

   @ApiParam(hidden = true)
   @ApiModelProperty(value = "视频格式")
   private String format;

   @ApiParam(hidden = true)
   @ApiModelProperty(value = "视频编码")
   private String codec;

   @ApiParam(hidden = true)
   @ApiModelProperty(value = "码率")
   private Integer rate;

   @ApiParam(hidden = true)
   @ApiModelProperty(value = "宽")
   private Integer width;

   @ApiParam(hidden = true)
   @ApiModelProperty(value = "高")
   private Integer height;

   @ApiParam(hidden = true)
   @ApiModelProperty(value = "审核状态(0待审核,1通过,2未通过)")
   private Integer audit;

   @ApiParam(hidden = true)
   @ApiModelProperty(value = "投诉状态(0正常,1被投诉)")
   private Integer status;

   @ApiModelProperty(value = "备注")
   @ApiParam(hidden = true)
   private String remark;

   @ApiParam(hidden = true)
   @ApiModelProperty(value = "删除标识(0未删除,1已删除)")
   @TableLogic
   private Integer deleted;

   @ApiParam(hidden = true)
   @ApiModelProperty(value = "创建时间")
   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
   private Date createdTime;

   @ApiParam(hidden = true)
   @ApiModelProperty(value = "更新时间")
   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
   private Date updatedTime;


}
