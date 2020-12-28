package com.ycandyz.master.domain.model.leafletTemplate;

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

/**
 * <p>
 * @Description 模板内容表 Model
 * </p>
 *
 * @author WenXin
 * @since 2020-12-18
 * @version 2.0
 */
@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description="模板内容表-Model")
public class TemplateOriginalModel implements Serializable {

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

   @ApiModelProperty(name = "user_id", value = "创建人")
   private Long userId;

   @ApiModelProperty(name = "shop_no", value = "商店编号")
   private String shopNo;

   @ApiModelProperty(name = "components", value = "模板组件")
   private String components;

   @ApiModelProperty(name = "classify_type", value = "类别标识（0：报名表，1：投票管理，2：问卷调查，3：信息收集）")
   private Integer classifyType;

   @ApiModelProperty(name = "created_time", value = "创建时间")
   private Date createdTime;

   @ApiModelProperty(name = "updated_time", value = "更新时间")
   private Date updatedTime;


}

