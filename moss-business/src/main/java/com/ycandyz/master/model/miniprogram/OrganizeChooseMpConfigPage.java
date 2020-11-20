package com.ycandyz.master.model.miniprogram;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrganizeChooseMpConfigPage {

    @ApiModelProperty(value = "id")
    private Integer menuId;

    @ApiModelProperty(value = "id")
    private String menuName;

    @ApiModelProperty(value = "菜单")
    private List<OrganizeMpConfigPageMenuVO> modules;

}
