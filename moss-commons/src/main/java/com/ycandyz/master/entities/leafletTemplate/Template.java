package com.ycandyz.master.entities.leafletTemplate;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

/**
 * <p>
 * @Description 模板定义表 数据表字段映射类
 * </p>
 *
 * @author WenXin
 * @since 2020-12-18
 * @version 2.0
 */
@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@TableName("template")
@ApiModel(description="模板定义表")
public class Template extends Model {

   @ApiModelProperty(name = "id", value = "模板id")
   @TableId(value = "id", type = IdType.AUTO)
   private Long id;

   @ApiModelProperty(name = "template_name", value = "模板名称")
   private String templateName;

   @ApiModelProperty(name = "share_title", value = "分享标题")
   private String shareTitle;

   @ApiModelProperty(name = "share_desc", value = "分享描述")
   private String shareDesc;

   @ApiModelProperty(name = "share_img", value = "分享配图")
   private String shareImg;

   @ApiModelProperty(name = "template_img", value = "模板配图")
   private String templateImg;

   @ApiModelProperty(name = "submit_restriction", value = "提交次数限制(默认为1次)")
   private Integer submitRestriction;

   @ApiModelProperty(name = "template_status", value = "模板状态（0:正常，1:删除）")
   private Integer templateStatus;

   @ApiModelProperty(name = "user_id", value = "创建人")
   private Long userId;

   @ApiModelProperty(name = "organize_id", value = "企业编号")
   private Long organizeId;

   @ApiModelProperty(name = "created_time", value = "创建时间")
   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
   private Date createdTime;

   @ApiModelProperty(name = "updated_time", value = "更新时间")
   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
   private Date updatedTime;

   @ApiModelProperty(name = "end_time", value = "失效时间")
   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
   private Date endTime;
}
