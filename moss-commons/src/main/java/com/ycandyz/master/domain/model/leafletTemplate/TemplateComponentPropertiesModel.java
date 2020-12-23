package com.ycandyz.master.domain.model.leafletTemplate;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 模板组件属性表Resp
 * @author WenXin
 * @since 2020-12-18
 * @version 2.0
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description="模板组件属性表-Model")
public class TemplateComponentPropertiesModel implements Serializable {

   private static final long serialVersionUID = 1L;

   @ApiModelProperty(name = "placeholder", value = "提示语")
   private String placeholder;

   @ApiModelProperty(name = "content_custom_length", value = "自定义长度")
   private Integer contentCustomLength;

   @ApiModelProperty(name = "content_max_length", value = "默认长度")
   private Integer contentMaxLength;

   @ApiModelProperty(name = "title", value = "标题")
   private String title;

   @ApiModelProperty(name = "is_required", value = "是否必填（0：是，1：否）")
   private Integer isRequired;
}

