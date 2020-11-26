package com.ycandyz.master.dto.miniprogram;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MoudelBasevo {

    @ApiModelProperty(value = "sortBase")
    private Integer sortBase;

    @ApiModelProperty(value = "baseName")
    private String baseName;

    @ApiModelProperty(value = "菜单模块元素编号")
    private String moduleBaseIds;

    @ApiModelProperty(value = "菜单模块元素编号")
    private String replacePicUrl;
}
