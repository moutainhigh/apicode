package com.ycandyz.master.domain.response.leafletTemplate;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 模板组件表 Resp
 * @author WenXin
 * @since 2020-12-18
 * @version 2.0
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description="默认模板组件表-Resp")
public class OriginalTemplateComponentResp implements Serializable {

   private static final long serialVersionUID = 1L;

   @ApiModelProperty(name = "component_name", value = "组件名称")
   private String componentName;

   @ApiModelProperty(name = "component_type", value = "组件类型(0:文本，1:下拉，2：复选，3:图片，4:时间，5：大文本)")
   private Integer componentType;

   @ApiModelProperty(name = "component_content", value = "组件内容",dataType = "java.lang.String")
   private String componentContent;

   @ApiModelProperty(name = "component_properties", value = "组件属性")
   private TemplateComponentPropertiesResp componentProperties;

   @ApiModelProperty(name = "component_order", value = "组件排序")
   private Integer componentOrder;

}

