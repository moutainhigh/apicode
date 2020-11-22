package com.ycandyz.master.model.miniprogram;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrganizeChooseMpConfigPage {

    @ApiModelProperty(value = "menuId")
    private Integer menuId;

    @ApiModelProperty(value = "menuName")
    private String menuName;

    @ApiModelProperty(value = "外部元素")
    private List<OrganizeMpConfigPageMenuVO> modules;

}
