package com.ycandyz.master.dto.organize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrganizeDTO {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "组织短名称")
    private String shortName;

    @ApiModelProperty(value = "组织全称")
    private String fullNname;

}
