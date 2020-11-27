package com.ycandyz.master.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * @Description  VO
 * </p>
 *
 * @author Wang Yang
 * @since 2020-11-15
 * @version 2.0
 */
@Data
public class OrganizeMpConfigModuleBaseVo {

    @ApiModelProperty(value = "id")
    private Integer id;

//    @ApiModelProperty(value = "organize_mp_config_plan_page的主键id")
//    private Integer organizeMpConfigPlanPageId;

    @ApiModelProperty(value = "元素编码")
    private Integer baseCode;

    @ApiModelProperty(value = "元素名称")
    private String baseName;

    @ApiModelProperty(value = "元素排序")
    private Integer sortBase;

    @ApiModelProperty(value = "展示样式 [1->平铺，2->滑动；3->丰富；4->简化]")
    private Integer showLayout;

    @ApiModelProperty(value = "展示数量限制")
    private Integer displayNum;
}
