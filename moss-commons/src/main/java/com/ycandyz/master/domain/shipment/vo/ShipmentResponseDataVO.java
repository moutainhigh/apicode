package com.ycandyz.master.domain.shipment.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ApiModel(value="快递100-订阅回调接口返回接口", description="订阅回调接口返回接口VO")
public class ShipmentResponseDataVO {

    @ApiModelProperty(value = "true表示成功，false表示失败。如果提交回调接口的地址失败，30分钟后重新回调，3次仍旧失败的，自动放弃")
    private boolean result;
    @ApiModelProperty(value = "200: 提交成功 500: 服务器错误 其他错误请自行定义")
    private String returnCode;
    @ApiModelProperty(value = "返回的提示")
    private String message;

    public static ShipmentResponseDataVO success(String message){
        return new ShipmentResponseDataVO(true,"200",message);
    }

    public static ShipmentResponseDataVO failed(String message){
        return new ShipmentResponseDataVO(false, "500", message);
    }
}
