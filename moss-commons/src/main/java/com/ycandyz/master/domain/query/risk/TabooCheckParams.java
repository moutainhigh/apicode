package com.ycandyz.master.domain.query.risk;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TabooCheckParams {



    @ApiModelProperty(value = "商品详情Id")
    private String itemNo;

    @ApiModelProperty(value = "商友圈Id")
    private Integer footprintId;

    @ApiModelProperty(value = "企业动态Id")
    private Integer organizeNewsId;

    @ApiModelProperty(value = "摘要")
    private String abstracts;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "商品名称")
    private String itemName;

    @ApiModelProperty(value = "商品描述")
    private String itemText;

    @ApiModelProperty(value = "所属模块")
    private Integer type;
}
