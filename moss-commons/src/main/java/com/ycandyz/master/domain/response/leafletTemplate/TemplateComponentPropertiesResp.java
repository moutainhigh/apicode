package com.ycandyz.master.domain.response.leafletTemplate;

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
@ApiModel(description="模板组件属性表-Resp")
public class TemplateComponentPropertiesResp implements Serializable {

   private static final long serialVersionUID = 1L;

   @ApiModelProperty(name = "placeholder", value = "提示语")
   private String placeholder;

   @ApiModelProperty(name = "content_max_length", value = "长度")
   private Integer contentMaxLength;

   @ApiModelProperty(name = "title", value = "标题")
   private String title;

   @ApiModelProperty(name = "title_max_length", value = "标题")
   private String titleMaxLength;

   @ApiModelProperty(name = "title_max_length", value = "标题")
   private String placeholderMaxLength;

   @ApiModelProperty(name = "content_custom_length", value = "标题")
   private String contentCustomLength;

   @ApiModelProperty(name = "is_required", value = "是否必填（0：是，1：否）")
   private Integer isRequired;
}

