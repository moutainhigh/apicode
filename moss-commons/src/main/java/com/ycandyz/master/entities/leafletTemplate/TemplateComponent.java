package com.ycandyz.master.entities.leafletTemplate;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Description 模板组件表 数据表字段映射类
 * @author WenXin
 * @since 2020-12-18
 * @version 2.0
 */
@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@TableName("template_component")
@ApiModel(description="模板组件表")
public class TemplateComponent extends Model {

   @ApiModelProperty(name = "id", value = "组件id")
   @TableId(value = "id", type = IdType.AUTO)
   private Long id;

   @ApiModelProperty(name = "parent_id", value = "父级id")
   private Integer parentId;

   @ApiModelProperty(name = "component_name", value = "组件名称")
   private String componentName;

   @ApiModelProperty(name = "component_type", value = "组件类型(0:文本，1:下拉，2：复选，3:图片，4:时间，5：大文本)")
   private Integer componentType;

   @ApiModelProperty(name = "component_properties", value = "组件属性")
   private String componentProperties;

   @ApiModelProperty(name = "component_status", value = "组件状态（0:正常，1:删除）")
   private Boolean componentStatus;

   @ApiModelProperty(name = "created_time", value = "创建时间")
   private Date createdTime;

   @ApiModelProperty(name = "updated_time", value = "更新时间")
   private Date updatedTime;

}
