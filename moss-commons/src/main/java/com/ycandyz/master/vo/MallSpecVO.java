package com.ycandyz.master.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @Description: 规格模版
 * @Author li Zhuo
 * @Date 2020/10/9
 * @Version: V1.0
*/

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="mall_spec对象", description="规格模版表")
public class MallSpecVO {

    @ApiModelProperty(value = "规格模版编号")
    private String specNo;

    @ApiModelProperty(value = "规格名称")
    private String specName;

    @ApiModelProperty(value = "规格模版值")
    private String[] specValues;

    @ApiModelProperty(value = "createdTime")
    private String createdTime;

    @ApiModelProperty(value = "updatedTime")
    private String updatedTime;
}
