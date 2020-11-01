package com.ycandyz.master.domain.query.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * @Description 用户资源 Query
 * </p>
 *
 * @author SanGang
 * @since 2020-10-30
 * @version 2.0
 */
@Getter
@Setter
public class UserNodeQuery implements Serializable {

    @ApiModelProperty(value = "0-企业 1-前端 2-代理商 3-有传 4-U客后台 5-U客App")
    private Integer platform;

    @ApiModelProperty(value = "企业id")
    private Long organizeId;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "菜单id")
    private Long menuId;

}
