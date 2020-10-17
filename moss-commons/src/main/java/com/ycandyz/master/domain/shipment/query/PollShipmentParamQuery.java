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
@ApiModel(description="快递100-订阅传参类")
public class PollShipmentParamQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订阅的快递公司的编码，一律用小写字母")
    private String company;
    @ApiModelProperty(value = "订阅的快递单号，单号的最大长度是32个字符")
    private String number;
    @ApiModelProperty(value = "出发地城市，省-市-区，非必填，填了有助于提升签收状态的判断的准确率，请尽量提供")
    private String from;
    @ApiModelProperty(value = "目的地城市，省-市-区，非必填，填了有助于提升签收状态的判断的准确率，且到达目的地后会加大监控频率，请尽量提供")
    private String to;
    @ApiModelProperty(value = "授权码，请到快递100页面申请企业版接口获取")
    private String key;
    @ApiModelProperty(value = "附加参数信息")
    private PollShipmentParametersQuery parameters;
}
