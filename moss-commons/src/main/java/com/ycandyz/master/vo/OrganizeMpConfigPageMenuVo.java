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

    @ApiModelProperty(value = "模块编号")
    private Integer moduleId;

    @ApiModelProperty(value = "模块名称")
    private String moduleName;

    @ApiModelProperty(value = "排序")
    private Integer sortModule;

    @ApiModelProperty(value = "展示数量限制")
    private Integer displayNum;

    @ApiModelProperty(value = "元素")
    private List<OrganizeMpConfigModuleBaseVo> baseInfo;

    @ApiModelProperty(value = "是否删除[0：否；1：是]")
    private Integer isDel;

}
