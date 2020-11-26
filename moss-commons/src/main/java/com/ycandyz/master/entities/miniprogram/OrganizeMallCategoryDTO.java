package com.ycandyz.master.entities.miniprogram;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 商品分类表
 * @Author li Zhuo
 * @Date 2020/10/9
 * @Version: V1.0
*/
@Data
public class OrganizeMallCategoryDTO {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "分类编号")
    private String categoryNo;

    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    @ApiModelProperty(value = "分类名称")
    private String categoryImg;

}
