package com.ycandyz.master.domain.response.leafletTemplate;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *
 * @author WenXin
 * @version 2.0
 * @Description 模板内容表 Resp
 * </p>
 * @since 2020-12-18
 */
@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description = "模板内容表-Resp")
public class TemplateOriginalResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "id", value = "默认模板id")
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

    @ApiModelProperty(name = "components", value = "模板组件")
    private List<OriginalTemplateComponentResp> components;

    @ApiModelProperty(name = "classify_name", value = "模板类别")
    private String classifyName;

    @ApiModelProperty(name = "classify_id", value = "模板类别")
    private Integer classifyId;

    @ApiModelProperty(name = "max_components_count", value = "最大组件数")
    private Integer maxComponentsCount;

}

