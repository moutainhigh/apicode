package com.ycandyz.master.domain.query.risk;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ContentReviewQuery {


    @ApiModelProperty(value = "内容模块")
    private Integer type;

    @ApiModelProperty(value = "更新开始时间")
    private Long updateTimeStart;

    @ApiModelProperty(value = "更新结束时间")
    private Long updateTimeEnd;

    @ApiModelProperty(value = "审核结果 [根据商品详情status 60:屏蔽、企业动态is_del 2:屏蔽、商友圈的状态判断is_del 2:屏蔽]")
    private Integer auditResult;

    @ApiModelProperty(value = "审核开始时间")
    private Long auditTimeStart;

    @ApiModelProperty(value = "审核结束时间")
    private Long auditTimeEnd;

    @ApiModelProperty(value = "审核人")
    private String auditor;

    @ApiModelProperty(value = "审核结果 [内容审核表审核结果，该条记录是否审核过， 1:待审核 2:已审核]")
    private Integer ReviewAuditResult;

}
