package com.ycandyz.master.domain.response.leafletTemplate;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author WenXin
 * @version 2.0
 * @Description 模板内容表 Resp
 * @since 2020-12-18
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description = "模板内容表-Resp")
public class TemplateClassifyResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "id", value = "类别id")
    private Long id;

    @ApiModelProperty(name = "classify_name", value = "类别名称")
    private Integer classifyName;

    @ApiModelProperty(name = "classify_type", value = "类别标识（0：报名表，1：投票管理，2：问卷调查，3：信息收集）")
    private Integer classifyType;

    @ApiModelProperty(name = "classify_img", value = "分类配图")
    private String classifyImg;

    @ApiModelProperty(name = "max_components_count", value = "最大组件数量")
    private String maxComponentsCount;

    @ApiModelProperty(name = "created_time", value = "创建时间")
    private Date createdTime;

    @ApiModelProperty(name = "updated_time", value = "更新时间")
    private Date updatedTime;

}

