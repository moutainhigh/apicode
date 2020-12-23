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
public class TemplateClassifyModel implements Serializable {

   private static final long serialVersionUID = 1L;

   @ApiModelProperty(name = "id", value = "类别id")
   private Long id;

   @ApiModelProperty(name = "classify_name", value = "类别名称")
   private Integer classifyName;

   @ApiModelProperty(name = "classify_type", value = "类别标识（0：报名表，1：投票管理，2：问卷调查，3：信息收集）")
   private Integer classifyType;

   @ApiModelProperty(name = "classify_img", value = "分类配图")
   private String classifyImg;

   @ApiModelProperty(name = "created_time", value = "创建时间")
   private Date createdTime;

   @ApiModelProperty(name = "updated_time", value = "更新时间")
   private Date updatedTime;


}

