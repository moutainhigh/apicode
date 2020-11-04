package com.ycandyz.master.domain.query.risk;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ContentReviewQuery {


    @ApiModelProperty(value = "内容模块")
    private Integer contentMoudle;

    @ApiModelProperty(value = "更新开始时间")
    private Long updateTimeStart;

    @ApiModelProperty(value = "更新结束时间")
    private Long updateTimeEnd;

    @ApiModelProperty(value = "审核结果")
    private Integer auditResult;

    @ApiModelProperty(value = "审核开始时间")
    private Long auditTimeStart;

    @ApiModelProperty(value = "审核结束时间")
    private Long auditTimeEnd;

    @ApiModelProperty(value = "审核人")
    private String auditor;

}
