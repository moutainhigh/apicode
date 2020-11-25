package com.ycandyz.master.model.miniprogram;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * @Description 企业小程序配置-菜单配置 VO
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */
@Data
public class OrganizeMpConfigPageSingleMenuVO {

    @ApiModelProperty(value = "menuId")
    private Integer menuId;

    @ApiModelProperty(value = "menuName")
    private String menuName;

    @ApiModelProperty(value = "modules")
    private  List<OrganizeMpConfigModuleVO>  modules;
}
