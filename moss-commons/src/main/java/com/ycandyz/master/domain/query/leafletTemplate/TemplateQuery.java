package com.ycandyz.master.domain.query.leafletTemplate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ycandyz.master.annotation.Condition;
import com.ycandyz.master.enums.ConditionEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

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

    @ApiModelProperty(name = "begin_create_time", value = "创建时间起")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Condition(field = "created_time", condition = ConditionEnum.LE)
    private Date beginCreateTime;

    @ApiModelProperty(name = "end_create_time", value = "创建时间止")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Condition(field = "created_time", condition = ConditionEnum.GE)
    private Date endCreateTime;

    @ApiModelProperty(name = "begin_expire_time", value = "失效时间起")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Condition(field = "endTime", condition = ConditionEnum.GE)
    private Date beginExpireTime;
    
    @ApiModelProperty(name = "end_expire_time", value = "失效时间止")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Condition(field = "endTime", condition = ConditionEnum.LE)
    private Date endExpireTime;

    @ApiModelProperty(name = "classify_id", value = "模板类别id")
    private Integer classifyId;

}
