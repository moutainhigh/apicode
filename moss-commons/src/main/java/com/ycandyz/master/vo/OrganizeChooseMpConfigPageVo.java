package com.ycandyz.master.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrganizeChooseMpConfigPageVo {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "菜单")
    private List<OrganizeMpConfigPageMenuVo> pageShowManage;

}
