package com.ycandyz.master.domain.query.organize;

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
public class OrganizeNewsQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "组织id")
    private Long organizeId;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "摘要")
    private String abstracts;



}
