package com.ycandyz.master.model.mall;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ApiModel(value="商家寄出的快递表", description="商家寄出的快递表查询VO")
public class MallShopShippingVO {

    @ApiModelProperty(value = "商家寄出的快递表id")
    private Long id;
    @ApiModelProperty(value = "订单编号")
    private String orderNo;
    @ApiModelProperty(value = "商家快递表编号")
    private String shopShippingNo;
    @ApiModelProperty(value = "10-快递  20-线下配送")
    private Integer type;
    @ApiModelProperty(value = "快递公司编码")
    private String companyCode;
    @ApiModelProperty(value = "快递公司名")
    private String company;
    @ApiModelProperty(value = "快递单号")
    private String number;
    @ApiModelProperty(value = "运费")
    private BigDecimal shippingMoney;
    @ApiModelProperty(value = "买家收货人姓名")
    private String receiver;
    @ApiModelProperty(value = "买家收货人头像")
    private String receiverHeadimg;
    @ApiModelProperty(value = "买家收货人手机号")
    private String receiverPhone;
    @ApiModelProperty(value = "买家收货地址")
    private String receiverAddress;
    @ApiModelProperty(value = "createdTime")
    private Date createdTime;
    @ApiModelProperty(value = "updatedTime")
    private Date updatedTime;
    @ApiModelProperty(value = "状态 10-有效  20-无效")
    private Integer status;
    @ApiModelProperty(value = "快递订阅推送状态 1-成功 2-失败")
    private Integer pollState;
}
