package com.ycandyz.master.domain.model.miniprogram;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author: WangYang
 * @Date: 2020/11/15 23:33
 * @Description: 模块内元素-参数
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="小程序配置模块内元素-参数")
public class ElementWithinModule {

    @ApiModelProperty(value = "元素编号")
    private Integer moduleBaseId;

    @ApiModelProperty(value = "展示样式")
    private Integer showLayout;

    @ApiModelProperty(value = "元素排序")
    private Integer sortBase;
}
