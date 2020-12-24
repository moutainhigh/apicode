package com.ycandyz.master.domain.response.leafletTemplate;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author WenXin
 * @version 2.0
 * @Description 模板内容表 Resp
 * @since 2020-12-18
 */
@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description = "模板内容表-Resp")
public class TemplateContentResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "id", value = "内容id")
    private Long id;

    @ApiModelProperty(name = "template_id", value = "模板id")
    private Integer templateId;

    @ApiModelProperty(name = "component_content", value = "组件内容")
    private List<TemplateTableContentResp> componentContents;

    @ApiModelProperty(name = "user_id", value = "创建人")
    private Long userId;

    @ApiModelProperty(name = "channel", value = "内容来源（0:小程序，1:公众号，2:app）")
    private Integer channel;

    @ApiModelProperty(name = "platform", value = "平台系统（0:android，1:ios）")
    private Integer platform;

    @ApiModelProperty(name = "created_time", value = "创建时间")
    private Date createdTime;

    @ApiModelProperty(name = "updated_time", value = "更新时间")
    private Date updatedTime;

    @ApiModelProperty(name = "user_phone", value = "用户手机号")
    private String userPhone;

}
