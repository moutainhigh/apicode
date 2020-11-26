package com.ycandyz.master.domain.query.risk;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReviewParam {

    @ApiModelProperty(value = "content_review主键id", required = true)
    private Long id;

    @ApiModelProperty(value = "内容模块[0:商品详情(表:mall_item);1:商友圈(表:footprint);2:企业动态(表:organize_news)]" ,required = true)
    private Integer type;

    @ApiModelProperty(value = "审核结果 [商品详情is_screen 系统屏蔽 0-通过 1-屏蔽;企业动态is_screen 是否删除 0-正常 1-系统屏蔽；商友圈is_screen 是否删除 0-否  1-系统屏蔽]")
    private Integer oper;

}
