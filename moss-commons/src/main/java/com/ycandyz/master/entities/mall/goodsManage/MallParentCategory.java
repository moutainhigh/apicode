package com.ycandyz.master.entities.mall.goodsManage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MallParentCategory {

    @ApiModelProperty(value = "分类编号")
    private String categoryNo;

    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    @ApiModelProperty(value = "分类图片")
    private String categoryImg;
}
