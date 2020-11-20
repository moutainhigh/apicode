package com.ycandyz.master.entities.risk;


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
@TableName("content_review")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="content_review对象", description="内容审核表")
public class ContentReview {

    @ApiModelProperty(value = "id")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "'商友圈id/商品详情Id/企业动态id'")
    private String contentId;

    @ApiModelProperty(value = "内容模块[0:商品详情;1:商友圈;2:企业动态]")
    private Integer type;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;

    @ApiModelProperty(value = "审核人")
    private Long auditor;

    @ApiModelProperty(value = "审核时间")
    private Date auditTime;

    @ApiModelProperty(value = "审核结果 0:未通过敏感词检测 1:待审核 2:已审核'")
    private Integer auditResult;

}
