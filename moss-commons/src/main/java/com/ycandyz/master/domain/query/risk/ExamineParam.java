package com.ycandyz.master.domain.query.risk;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ExamineParam {

    @ApiModelProperty(value = "content_review主键id")
    private Long id;

    @ApiModelProperty(value = "内容模块[0:商品详情(表:mall_item);1:商友圈(表:footprint);2:企业动态(表:organize_news)]")
    private Integer type;

}
