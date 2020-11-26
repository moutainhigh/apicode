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
public class OrganizeMallCategoryVO {

    @ApiModelProperty(value = "一级分类编号")
    private String categoryNo;

    @ApiModelProperty(value = "一级分类图片地址")
    private String imgUrl;

}
