package com.ycandyz.master.domain.response.leafletTemplate;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Description 模板明细表 Resp
 * @author WenXin
 * @since 2020-12-18
 * @version 2.0
 */
@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description="模板明细表-列表动态列-Resp")
public class TemplateTableResp implements Serializable {

   private static final long serialVersionUID = 1L;

   @ApiModelProperty(name = "detail_id", value = "模板明细id")
   private Long detail_id;

   @ApiModelProperty(name = "component_type", value = "组件类型(0:文本，1:下拉，2：复选，3:图片，4:时间)")
   private Integer componentType;

   @ApiModelProperty(name = "component_title", value = "组件类型(0:文本，1:下拉，2：复选，3:图片，4:时间)")
   private String componentTitle;

   @ApiModelProperty(name = "component_order", value = "组件顺序")
   private Integer componentOrder;

}

