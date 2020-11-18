package com.ycandyz.master.model.miniprogram;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ycandyz.master.annotation.Condition;
import com.ycandyz.master.entities.miniprogram.MpConfigModule;
import com.ycandyz.master.enums.ConditionEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <p>
 * @Description  VO
 * </p>
 *
 * @author Wang Yang
 * @since 2020-11-15
 * @version 2.0
 */
@Getter
@Setter
@TableName("mp_config_module")
public class OrganizeMpConfigModuleVO extends MpConfigModule {


    @ApiModelProperty(value = "编号")
    @Condition(field = "id", condition = ConditionEnum.EQ)
    private Integer id;

    @ApiModelProperty(value = "元素模版名称")
    private String moduleName;

    @ApiModelProperty(value = "限制展示数量")
    private Integer displayNum;

    @ApiModelProperty(value = "元素名称")
    private String baseName;

    private List<OrganizeMpConfigModuleBaseVO> organizeMpConfigModuleBaseVOS;
}
