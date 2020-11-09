package com.ycandyz.master.domain.query.risk;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReviewExamineParam {

    @ApiModelProperty(value = "内容id 商友圈id/商品详情Id/企业动态id")
    private Long contentId;

    @ApiModelProperty(value = "审核操作 [商品详情is_del 系统屏蔽 0-通过 2-屏蔽;企业动态is_del 是否删除 0-正常 2-系统屏蔽；商友圈 is_del 是否删除 0-否  2-系统屏蔽]")
    private Integer oper;

}
