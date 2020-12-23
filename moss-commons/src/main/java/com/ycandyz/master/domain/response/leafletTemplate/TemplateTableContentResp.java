package com.ycandyz.master.domain.response.leafletTemplate;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author WenXin
 * @version 2.0
 * @Description 模板动态表格内容表 Resp
 * @since 2020-12-18
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description = "模板动态表格内容表-Resp")
public class TemplateTableContentResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "detail_id", value = "模板明细id")
    private Long detailId;

    @ApiModelProperty(name = "content", value = "模板组件内容")
    private String content;

    @ApiModelProperty(name = "title", value = "模板组件标题")
    private String title;

}

