package com.ycandyz.master.entities.leafletTemplate;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @Description 模板内容表 数据表字段映射类
 * @author WenXin
 * @since 2020-12-18
 * @version 2.0
 */
@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@TableName("template_content")
@ApiModel(description="模板内容表")
public class TemplateContent extends Model {

   @ApiModelProperty(name = "id", value = "内容id")
   @TableId(value = "id", type = IdType.AUTO)
   private Long id;

   @ApiModelProperty(name = "template_id", value = "模板id")
   private Integer templateId;

   @ApiModelProperty(name = "component_content", value = "组件内容")
   private String componentContent;

   @ApiModelProperty(name = "user_id", value = "创建人")
   private Long userId;

   @ApiModelProperty(name = "channel", value = "内容来源（0:小程序，1:公众号，2:app）")
   private Integer channel;

   @ApiModelProperty(name = "platform", value = "平台系统（0:android，1:ios）")
   private Integer platform;

   @ApiModelProperty(name = "created_time", value = "创建时间")
   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
   private Date createdTime;

   @ApiModelProperty(name = "updated_time", value = "更新时间")
   private Date updatedTime;


}
