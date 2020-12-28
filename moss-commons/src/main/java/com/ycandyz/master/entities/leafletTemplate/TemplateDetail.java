package com.ycandyz.master.entities.leafletTemplate;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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

/**
 * <p>
 * @Description 模板明细表 数据表字段映射类
 * </p>
 *
 * @author WenXin
 * @since 2020-12-18
 * @version 2.0
 */
@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@TableName("template_detail")
@ApiModel(description="模板明细表")
public class TemplateDetail extends Model {

   @ApiModelProperty(name = "id", value = "明细id")
   @TableId(value = "id", type = IdType.AUTO)
   private Long id;

   @ApiModelProperty(name = "template_id", value = "模板id")
   private Long templateId;

   @ApiModelProperty(name = "component_content", value = "组件内容")
   private String componentContent;

   @ApiModelProperty(name = "component_type", value = "组件类型(0:文本，1:下拉，2：复选，3:图片，4:时间)")
   private Integer componentType;

   @ApiModelProperty(name = "component_properties", value = "组件属性")
   private String componentProperties;

   @ApiModelProperty(name = "component_status", value = "状态（0:正常，1:删除）")
   private Integer componentStatus;

   @ApiModelProperty(name = "component_order", value = "组件顺序")
   private Integer componentOrder;

   @ApiModelProperty(name = "created_time", value = "创建时间")
   private Date createdTime;

   @ApiModelProperty(name = "updated_time", value = "更新时间")
   private Date updatedTime;


}
