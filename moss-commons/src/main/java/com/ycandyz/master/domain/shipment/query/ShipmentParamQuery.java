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
@ApiModel(description="快递100-订阅回调接口传参")
public class ShipmentParamQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "监控状态:polling:监控中，shutdown:结束，abort:中止，updateall：重新推送。其中当快递单为已签收时status=shutdown，当message为“3天查询无记录”或“60天无变化时”status= abort ，对于status=abort的状度，需要增加额外的处理逻辑")
    private String status;
    @ApiModelProperty(value = "包括got、sending、check三个状态，由于意义不大，已弃用，请忽略")
    private String billstatus;
    @ApiModelProperty(value = "监控状态相关消息，如:3天查询无记录，60天无变化")
    private String message;
    @ApiModelProperty(value = "快递公司编码是否出错，0为本推送信息对应的是贵司提交的原始快递公司编码，1为本推送信息对应的是我方纠正后的新的快递公司编码。" +
            "一个单如果我们连续3天都查不到结果，我方会（1）判断一次贵司提交的快递公司编码是否正确，如果正确，给贵司的回调接口（callbackurl）推送带有如下字段的信息：" +
            "autoCheck=0、comOld与comNew都为空；（2）如果贵司提交的快递公司编码出错，我们会帮忙用正确的快递公司编码+原来的运单号重新提交订阅并开启监控（后续如果监控到单号有更新就给贵司的回调接口" +
            "（callbackurl）推送带有如下字段的信息：autoCheck=1、comOld=原来的公司编码、comNew=新的公司编码）；并且给贵方的回调接口（callbackurl）推送一条含有如下字段的信息：status=abort、autoCheck=0、comOld为空、comNew=纠正后的快递公司编码。")
    private String autoCheck;
    @ApiModelProperty(value = "贵司提交的原始的快递公司编码。详细见autoCheck后说明。若开启了国际版（即在订阅请求中增加字段interCom=1），则回调请求中暂无此字段")
    private String comOld;
    @ApiModelProperty(value = "我司纠正后的新的快递公司编码。详细见autoCheck后说明。若开启了国际版（即在订阅请求中增加字段interCom=1），则回调请求中暂无此字段")
    private String comNew;
    @ApiModelProperty(value = "最新查询结果，若在订阅报文中通过interCom字段开通了国际版，则此lastResult表示出发国的查询结果，全量，倒序（即时间最新的在最前）")
    private ShipmentParamLastResultQuery lastResult;
}
