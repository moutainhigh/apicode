package com.ycandyz.master.domain.query.miniprogram;

import com.ycandyz.master.annotation.Condition;
import com.ycandyz.master.enums.ConditionEnum;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * @Description 小程序配置方案 检索参数类
 * </p>
 *
 * @author WangYang
 * @since 2020-11-13
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="小程序配置方案-检索参数")
public class MpConfigPlanQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "方案名称")
    @Condition(field = "plan_name", condition = ConditionEnum.LIKE)
    private String planName;

    @ApiModelProperty(value = "是否同步U客")
    @Condition(field = "sync_uke", condition = ConditionEnum.EQ)
    private Boolean syncUke;

}
