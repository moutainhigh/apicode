package com.ycandyz.master.dto.miniprogram;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 小程序配置-页面配置 实体类
 * </p>
 *
 * @author Wang Yang
 * @since 2020-11-15
 * @version 2.0
 */
@Getter
@Setter
@TableName("mp_config_plan_page")
public class MpConfigPlanPageBaseDTO {

    @ApiModelProperty(value = "元素编号")
    private Integer id;

    @ApiModelProperty(value = "元素基础编号")
    private Integer moduleBaseId;

    @ApiModelProperty(value = "元素名称")
    private String baseName;

    @ApiModelProperty(value = "元素标识")
    private String baseCode;

    @ApiModelProperty(value = "元素排序值")
    private Integer sortBase;

    @ApiModelProperty(value = "元素布局方式")
    private Integer showLayout;

    @ApiModelProperty(value = "元素显示数量")
    private Integer displayNum;

}
