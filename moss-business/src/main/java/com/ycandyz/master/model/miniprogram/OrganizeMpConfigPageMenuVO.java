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
public class OrganizeMpConfigPageMenuVO {

    @ApiModelProperty(value = "编号")
    private Integer id;

    @ApiModelProperty(value = "menuId")
    private Integer menuId;

    @ApiModelProperty(value = "menuName")
    private String menuName;

    @ApiModelProperty(value = "模块编号")
    private Integer moduleId;

    @ApiModelProperty(value = "模块名称")
    private String moduleName;

    @ApiModelProperty(value = "展示数量限制")
    private Integer displayNum;

    @ApiModelProperty(value = "排序")
    private Integer sortModule;

    @ApiModelProperty(value = "元素")
    private List<OrganizeMpConfigModuleBaseVO> baseInfo;
}
