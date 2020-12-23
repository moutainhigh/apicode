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
 * @Description 模板内容表 检索参数类
 * </p>
 *
 * @author WenXin
 * @since 2020-12-18
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="模板内容表-检索参数")
public class TemplateContentQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "id", value = "内容id")
    @Condition(field = "id", condition = ConditionEnum.EQ)
    private Long id;

    @ApiModelProperty(name = "template_id", value = "模板id")
    @Condition(condition = ConditionEnum.EQ)
    private Integer templateId;

    @ApiModelProperty(name = "component_content", value = "组件内容")
    @Condition(condition = ConditionEnum.EQ)
    private String componentContent;

    @ApiModelProperty(name = "user_id", value = "创建人")
    @Condition(condition = ConditionEnum.EQ)
    private Long userId;

    @ApiModelProperty(name = "channel", value = "内容来源（0:小程序，1:公众号，2:app）")
    @Condition(condition = ConditionEnum.EQ)
    private Boolean channel;

    @ApiModelProperty(name = "platform", value = "平台系统（0:android，1:ios）")
    @Condition(condition = ConditionEnum.EQ)
    private Boolean platform;

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
