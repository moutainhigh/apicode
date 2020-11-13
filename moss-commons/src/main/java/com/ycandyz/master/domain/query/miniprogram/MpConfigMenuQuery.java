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
 * @Description 小程序配置-菜单配置 检索参数类
 * </p>
 *
 * @author WangYang
 * @since 2020-11-13
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="小程序配置-菜单配置-检索参数")
public class MpConfigMenuQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称")
    private String title;

    @ApiModelProperty(value = "逻辑删除0：未删除；1、删除")
    private Boolean logicDelete;

}
