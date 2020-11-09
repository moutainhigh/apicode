package com.ycandyz.master.domain.query.risk;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ContentReviewQuery {


//    @ApiModelProperty(value = "内容模块 [0:商品详情(表:mall_item);1:商友圈(表:footprint);2:企业动态(表:organize_news)]")
//    private Integer type;

    @ApiModelProperty(value = "更新开始时间")
    private Long updateTimeStart;

    @ApiModelProperty(value = "更新结束时间")
    private Long updateTimeEnd;

    @ApiModelProperty(value = "审核结果 [商品详情is_del 系统屏蔽 0-通过 2-屏蔽;企业动态is_del 是否删除 0-正常 2-系统屏蔽；商友圈 is_del 是否删除 0-否  2-系统屏蔽]")
    private Integer auditResult;

    @ApiModelProperty(value = "审核开始时间")
    private Long auditTimeStart;

    @ApiModelProperty(value = "审核结束时间")
    private Long auditTimeEnd;

    @ApiModelProperty(value = "审核人")
    private Long auditor;

    @ApiModelProperty(value = "内容表审核表审核结果 [表示该条记录是否审核过， 1:待审核 2:已审核]")
    private Integer ReviewAuditResult;

}
