package com.ycandyz.master.model.miniprogram;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrganizeChooseMpConfigPage {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "菜单")
    private List<OrganizeMpConfigPageMenuVO> pageShowManage;

}
