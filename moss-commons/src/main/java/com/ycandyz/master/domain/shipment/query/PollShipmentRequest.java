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
@ApiModel(description="快递100-订阅传参类-root类")
public class PollShipmentRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订阅的快递接口传参格式")
    private String schema;
    @ApiModelProperty(value = "订阅的快递接口传参内容")
    private PollShipmentParamQuery param;
}
