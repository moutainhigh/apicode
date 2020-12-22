package com.ycandyz.master.domain.response.leafletTemplate;

import com.ycandyz.master.entities.leafletTemplate.TemplateOriginal;
import com.baomidou.mybatisplus.annotation.TableName;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * @Description 模板内容表 Resp
 * </p>
 *
 * @author WenXin
 * @since 2020-12-18
 * @version 2.0
 */
@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description="模板内容表-Resp")
public class TemplateOriginalResp implements Serializable {

   private static final long serialVersionUID = 1L;

   @ApiModelProperty(name = "id", value = "默认模板id")
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

   @ApiModelProperty(name = "components", value = "模板组件")
   private List<OriginalTemplateComponentResp> components;

}

