package com.ycandyz.master.domain.query.leafletTemplate;

import com.ycandyz.master.annotation.Condition;
import com.ycandyz.master.enums.ConditionEnum;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * @Description 模板组件表 检索参数类
 * </p>
 *
 * @author WenXin
 * @since 2020-12-18
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="模板组件表-检索参数")
public class TemplateComponentQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "id", value = "组件id")
    @Condition(field = "id", condition = ConditionEnum.EQ)
    private Long id;

    @ApiModelProperty(name = "parent_id", value = "父级id")
    @Condition(condition = ConditionEnum.EQ)
    private Integer parentId;

    @ApiModelProperty(name = "component_name", value = "组件名称")
    @Condition(condition = ConditionEnum.EQ)
    private String componentName;

    @ApiModelProperty(name = "component_type", value = "组件类型(0:文本，1:下拉，2：复选，3:图片，4:时间，5：大文本)")
    @Condition(condition = ConditionEnum.EQ)
    private Integer componentType;

    @ApiModelProperty(name = "component_properties", value = "组件属性")
    @Condition(condition = ConditionEnum.EQ)
    private String componentProperties;

    @ApiModelProperty(name = "component_status", value = "组件状态（0:正常，1:删除）")
    @Condition(condition = ConditionEnum.EQ)
    private Boolean componentStatus;

    @ApiModelProperty(name = "created_time", value = "创建时间起")
    @Condition(field = "created_time", condition = ConditionEnum.GE)
    private Date createdTimeS;
    
    @ApiModelProperty(name = "created_time", value = "创建时间止")
    @Condition(field = "created_time", condition = ConditionEnum.LE)
    private Date createdTimeE;

    @ApiModelProperty(name = "updated_time", value = "更新时间起")
    @Condition(field = "updated_time", condition = ConditionEnum.GE)
    private Date updatedTimeS;
    
    @ApiModelProperty(name = "updated_time", value = "更新时间止")
    @Condition(field = "updated_time", condition = ConditionEnum.LE)
    private Date updatedTimeE;



}
