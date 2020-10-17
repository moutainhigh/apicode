package com.ycandyz.master.domain.shipment.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="快递100-订阅回调接口传参lastResult下data类")
public class ShipmentParamLastResultDataQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "内容")
    private String context;
    @ApiModelProperty(value = "时间，原始格式2012-08-28 16:33:19")
    private String time;
    @ApiModelProperty(value = "格式化后时间2012-08-28 16:33:19")
    private String ftime;
    @ApiModelProperty(value = "本数据元对应的签收状态。在订阅接口中提交resultv2 = 1字段后才会出现")
    private String status;
    @ApiModelProperty(value = "本数据元对应的行政区域的编码，在订阅接口中提交resultv2 = 1字段后才会出现")
    private String areaCode;
    @ApiModelProperty(value = "本数据元对应的行政区域的名称，在订阅接口中提交resultv2 = 1字段后才会出现")
    private String areaName;
}
