package com.ycandyz.master.model.mall;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MallBuyerShippingLogVO {

    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "买家物流表编号")
    private String buyerShippingNo;
    @ApiModelProperty(value = "物流公司编码")
    private String companyCode;
    @ApiModelProperty(value = "快递公司名")
    private String company;
    @ApiModelProperty(value = "快递单号")
    private String number;
    @ApiModelProperty(value = "内容")
    private String context;
    @ApiModelProperty(value = "快递单当前状态，包括 0 在途，1 揽收，2 疑难，3 签收，4 退签，5 派件，6 退回，7 转单，10 待清关，11 清关中，12 已清关，13 清关异常，14 收件人拒签等")
    private Integer status;
    @ApiModelProperty(value = "是否签收标记  0-否  1-是")
    private Integer isCheck;
    @ApiModelProperty(value = "物流节点时间")
    private String logAt;
    @ApiModelProperty(value = "createdTime")
    private Date createdTime;
    @ApiModelProperty(value = "updatedTime")
    private Date updatedTime;
}
