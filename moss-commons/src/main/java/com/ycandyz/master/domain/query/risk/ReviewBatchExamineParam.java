package com.ycandyz.master.domain.query.risk;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ReviewBatchExamineParam {


    @ApiModelProperty(value = "审核结果 [商品详情is_screen 系统屏蔽 0-通过 1-屏蔽;企业动态is_screen 是否删除 0-正常 1-系统屏蔽；商友圈is_screen 是否删除 0-否  1-系统屏蔽]")
    private Integer oper;

    @ApiModelProperty(value = "contentId type")
    private List<ExamineParam> examineParams;

}
