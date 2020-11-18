package com.ycandyz.master.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserForExport {

    @ApiModelProperty(value = "操作人id")
    private Long id;

    @ApiModelProperty(value = "操作人姓名")
    private String name;

    @ApiModelProperty(value = "操作人手机号")
    private String phone;
}
