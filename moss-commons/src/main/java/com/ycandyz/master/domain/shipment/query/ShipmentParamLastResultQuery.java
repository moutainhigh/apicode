package com.ycandyz.master.domain.shipment.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="快递100-订阅回调接口传参lastResult类")
public class ShipmentParamLastResultQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "消息体，请忽略")
    private String message;
    @ApiModelProperty(value = "快递单当前状态，包括0在途，1揽收，2疑难，3签收，4退签，5派件，6退回，7转单，10待清关，11清关中，12已清关，13清关异常，14收件人拒签等13个状态")
    private String state;
    @ApiModelProperty(value = "通讯状态，请忽略")
    private String status;
    @ApiModelProperty(value = "快递单明细状态标记，暂未实现，请忽略")
    private String condition;
    @ApiModelProperty(value = "是否签收标记")
    private String ischeck;
    @ApiModelProperty(value = "快递公司编码,一律用小写字母")
    private String com;
    @ApiModelProperty(value = "单号")
    private String nu;
    @ApiModelProperty(value = "数组，包含多个对象，每个对象字段如展开所示")
    private List<ShipmentParamLastResultDataQuery> data;
}
