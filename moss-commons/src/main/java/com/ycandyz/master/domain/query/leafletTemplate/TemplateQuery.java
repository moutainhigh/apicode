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
 * @Description 模板定义表 检索参数类
 * </p>
 *
 * @author WenXin
 * @since 2020-12-18
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="模板定义表-检索参数")
public class TemplateQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "id", value = "模板id")
    @Condition(field = "id", condition = ConditionEnum.EQ)
    private Long id;

    @ApiModelProperty(name = "template_name", value = "模板名称")
    @Condition(condition = ConditionEnum.EQ)
    private String templateName;

    @ApiModelProperty(name = "share_title", value = "分享标题")
    @Condition(condition = ConditionEnum.EQ)
    private String shareTitle;

    @ApiModelProperty(name = "share_desc", value = "分享描述")
    @Condition(condition = ConditionEnum.EQ)
    private String shareDesc;

    @ApiModelProperty(name = "share_img", value = "分享配图")
    @Condition(condition = ConditionEnum.EQ)
    private String shareImg;

    @ApiModelProperty(name = "template_img", value = "模板配图")
    @Condition(condition = ConditionEnum.EQ)
    private String templateImg;

    @ApiModelProperty(name = "submit_restriction", value = "提交次数限制(默认为1次)")
    @Condition(condition = ConditionEnum.EQ)
    private Integer submitRestriction;

    @ApiModelProperty(name = "template_status", value = "模板状态（0:正常，1:删除）")
    @Condition(condition = ConditionEnum.EQ)
    private Integer templateStatus;

    @ApiModelProperty(name = "user_id", value = "创建人")
    @Condition(condition = ConditionEnum.EQ)
    private Long userId;

    @ApiModelProperty(name = "organize_id", value = "企业编号")
    @Condition(condition = ConditionEnum.EQ)
    private Long organizeId;

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

    @ApiModelProperty(name = "end_time", value = "失效时间起")
    @Condition(field = "end_time", condition = ConditionEnum.GE)
    private Date endTimeS;
    
    @ApiModelProperty(name = "end_time", value = "失效时间止")
    @Condition(field = "end_time", condition = ConditionEnum.LE)
    private Date endTimeE;



}
