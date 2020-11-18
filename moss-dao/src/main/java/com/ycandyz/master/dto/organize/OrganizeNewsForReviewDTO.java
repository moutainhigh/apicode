package com.ycandyz.master.dto.organize;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * @Description 企业动态 检索参数类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-27
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="企业动态-检索参数")
public class OrganizeNewsForReviewDTO implements Serializable {

    @ApiModelProperty(value = "摘要")
    private String abstracts;

    @ApiModelProperty(value = "内容")
    private String title;

}
