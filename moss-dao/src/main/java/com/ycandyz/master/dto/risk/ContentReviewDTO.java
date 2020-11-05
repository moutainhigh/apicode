package com.ycandyz.master.dto.risk;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ContentReviewDTO {


    @ApiModelProperty(value = "内容")
    private Object content;

    @ApiModelProperty(value = "内容模块 [0:商品详情(表:mall_item);1:商友圈(表:footprint);2:企业动态(表:organize_news)]")
    private Integer type;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    @ApiModelProperty(value = "审核人")
    private Long auditor;

    @ApiModelProperty(value = "审核时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date auditTime;

    @ApiModelProperty(value = "审核结果")
    private Integer auditResult;

}
