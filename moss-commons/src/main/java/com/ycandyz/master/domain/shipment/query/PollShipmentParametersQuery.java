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
@ApiModel(description="快递100-订阅传参parameter类")
public class PollShipmentParametersQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "回调接口的地址")
    private String callbackurl;
    @ApiModelProperty(value = "签名用随机字符串")
    private String salt;
    @ApiModelProperty(value = "添加此字段表示打开行政区域解析功能")
    private Integer resultv2;
    @ApiModelProperty(value = "添加此字段且将此值设为1，则表示开始智能判断单号所属公司的功能，开启后，company字段可为空,即只传运单号（number字段），我方收到后会根据单号判断出其所属的快递公司（即company字段）。建议只有在无法知道单号对应的快递公司（即company的值）的情况下才开启此功能")
    private Integer autoCom;
    @ApiModelProperty(value = "添加此字段表示开启国际版，开启后，若订阅的单号（即number字段）属于国际单号，会返回出发国与目的国两个国家的跟踪信息，本功能暂时只支持邮政体系（国际类的邮政小包、EMS）内的快递公司，若单号我方识别为非国际单，即使添加本字段，也不会返回destResult元素组")
    private Integer interCom;
    @ApiModelProperty(value = "出发国家编码")
    private String departureCountry;
    @ApiModelProperty(value = "出发的快递公司的编码")
    private String departureCom;
    @ApiModelProperty(value = "目的国家编码")
    private String destinationCountry;
    @ApiModelProperty(value = "目的的快递公司的编码")
    private String destinationCom;
    @ApiModelProperty(value = "收、寄件人的电话号码（手机和固定电话均可，只能填写一个，顺丰单号必填，其他快递公司选填。如座机号码有分机号，分机号无需上传。）")
    private String phone;

}
