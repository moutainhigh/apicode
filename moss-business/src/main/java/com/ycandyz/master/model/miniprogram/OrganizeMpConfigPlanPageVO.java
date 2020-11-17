package com.ycandyz.master.model.miniprogram;

import com.ycandyz.master.annotation.Condition;
import com.ycandyz.master.entities.miniprogram.OrganizeMpConfigPlanPage;
import com.baomidou.mybatisplus.annotation.TableName;

import com.ycandyz.master.enums.ConditionEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <p>
 * @Description 企业小程序配置-页面配置 VO
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */
@Getter
@Setter
@TableName("organize_mp_config_plan_page")
public class OrganizeMpConfigPlanPageVO extends OrganizeMpConfigPlanPage {

    @ApiModelProperty(value = "模块编号")
    private Integer moduleId;

    @ApiModelProperty(value = "展示样式")
    private Integer showLayout;

    @ApiModelProperty(value = "模块排序")
    private Integer sortModule;

    @ApiModelProperty(value = "元素排序")
    private Integer sortBase;

    @ApiModelProperty(value = "元素名称")
    private String baseName;


}
