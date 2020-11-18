package com.ycandyz.master.vo;

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
public class OrganizeMpConfigPageMenuVo {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "菜单编号")
    private Integer menuId;

    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @ApiModelProperty(value = "模块编号")
    private Integer moduleId;

    @ApiModelProperty(value = "模块名称")
    private String moduleName;

    @ApiModelProperty(value = "元素")
    private List<OrganizeMpConfigModuleBaseVo> baseInfo;

    @ApiModelProperty(value = "展示数量限制")
    private Integer displayNum;

}
