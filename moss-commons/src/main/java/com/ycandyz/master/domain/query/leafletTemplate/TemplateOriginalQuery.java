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
public class TemplateOriginalQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "id", value = "默认模板id")
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

    @ApiModelProperty(name = "user_id", value = "创建人")
    @Condition(condition = ConditionEnum.EQ)
    private Long userId;

    @ApiModelProperty(name = "shop_no", value = "商店编号")
    @Condition(condition = ConditionEnum.EQ)
    private String shopNo;

    @ApiModelProperty(name = "components", value = "模板组件")
    @Condition(condition = ConditionEnum.EQ)
    private String components;

    @ApiModelProperty(name = "classify_type", value = "类别标识（0：报名表，1：投票管理，2：问卷调查，3：信息收集）")
    @Condition(condition = ConditionEnum.EQ)
    private Integer classifyType;

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
