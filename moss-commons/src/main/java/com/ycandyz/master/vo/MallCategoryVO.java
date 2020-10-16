package com.ycandyz.master.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 商品分类表
 * @Author li Zhuo
 * @Date 2020/10/9
 * @Version: V1.0
*/
@Data
public class MallCategoryVO {

    @ApiModelProperty(value = "父分类编号")
    private String parentCategoryNo;
    @ApiModelProperty(value = "分类名称")
    private String categoryName;
    @ApiModelProperty(value = "分类图片")
    private String categoryImg;
}
