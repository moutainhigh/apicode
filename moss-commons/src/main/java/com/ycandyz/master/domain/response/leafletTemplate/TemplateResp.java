package com.ycandyz.master.domain.response.leafletTemplate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ycandyz.master.entities.leafletTemplate.Template;
import com.baomidou.mybatisplus.annotation.TableName;

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

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *
 * @author WenXin
 * @version 2.0
 * @Description 模板定义表 Resp
 * </p>
 * @since 2020-12-18
 */
@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description = "模板定义表-Resp")
public class TemplateResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "id", value = "模板id")
    private Long id;

    @ApiModelProperty(name = "template_name", value = "模板名称")
    private String templateName;

    @ApiModelProperty(name = "share_title", value = "分享标题")
    private String shareTitle;

    @ApiModelProperty(name = "share_desc", value = "分享描述")
    private String shareDesc;

    @ApiModelProperty(name = "share_img", value = "分享配图")
    private String shareImg;

    @ApiModelProperty(name = "template_img", value = "模板配图")
    private String templateImg;

    @ApiModelProperty(name = "submit_restriction", value = "提交次数限制(默认为1次)")
    private Integer submitRestriction;

    @ApiModelProperty(name = "template_status", value = "模板状态（1：发布，2：未发布，3：删除/过期）")
    private Integer templateStatus;

    @ApiModelProperty(name = "user_id", value = "创建人")
    private Long userId;

    @ApiModelProperty(name = "organize_id", value = "企业编号")
    private Integer organizeId;

    @ApiModelProperty(name = "created_time", value = "创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;

    @ApiModelProperty(name = "updated_time", value = "更新时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updatedTime;

    @ApiModelProperty(name = "end_time", value = "失效时间")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date endTime;

    @ApiModelProperty(name = "components", value = "组件信息")
    private List<TemplateDetailResp> components;

    @ApiModelProperty(name = "classify_name", value = "模板类别名称")
    private String classifyName;

    @ApiModelProperty(name = "classify_id", value = "模板类别id")
    private Long classifyId;

    @ApiModelProperty(name = "max_components_count", value = "最大组件数")
    private Integer maxComponentsCount;
}

