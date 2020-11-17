package com.ycandyz.master.domain.query.risk;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@TableName("content_review_log")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="content_review_log对象", description="内容审核日志表")
public class ContentReviewLogVO {

    @ApiModelProperty(value = "id")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "'商友圈id/商品详情Id/企业动态id'")
    private Long contentId;

    @ApiModelProperty(value = "内容模块[0:商品详情;1:商友圈;2:企业动态]")
    private Integer type;

    @ApiModelProperty(value = "审核人")
    private Long auditor;

    @ApiModelProperty(value = "审核结果 0:未通过敏感词检测 1:待审核 2:已审核'")
    private Integer auditResult;

    @ApiModelProperty(value = "内容模块审核结果 [0:正常 1:屏蔽]")
    private Integer contentAuditResult;

}
