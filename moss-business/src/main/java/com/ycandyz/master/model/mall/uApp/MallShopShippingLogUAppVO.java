package com.ycandyz.master.model.mall.uApp;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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
@ApiModel(value="商家寄出的快递物流日志表", description="商家寄出的快递物流日志表查询VO")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MallShopShippingLogUAppVO {

    /**id*/
    @ApiModelProperty(value = "id")
    private Long id;
    /**商家快递表编号*/
    @ApiModelProperty(value = "商家快递表编号")
    private String shopShippingNo;
    /**快递公司编号*/
    @ApiModelProperty(value = "快递公司编号")
    private String companyCode;
    /**快递公司名*/
    @ApiModelProperty(value = "快递公司名")
    private String company;
    /**快递单号*/
    @ApiModelProperty(value = "快递单号")
    private String number;
    /**内容*/
    @ApiModelProperty(value = "内容")
    private String context;
    /**行政区域的编码*/
    @ApiModelProperty(value = "行政区域的编码")
    private String areaCode;
    /**行政区域的名称*/
    @ApiModelProperty(value = "行政区域的名称")
    private String areaName;
    /**运费*/
    @ApiModelProperty(value = "运费")
    private BigDecimal shippingMoney;
    /**快递单当前状态，包括 0 在途，1 揽收，2 疑难，3 签收，4 退签，5 派件，6 退回，7 转单，10 待清关，11 清关中，12 已清关，13 清关异常，14 收件人拒签等 */
    @ApiModelProperty(value = "快递单当前状态，包括 0 在途，1 揽收，2 疑难，3 签收，4 退签，5 派件，6 退回，7 转单，10 待清关，11 清关中，12 已清关，13 清关异常，14 收件人拒签等 ")
    private Integer status;
    /**是否签收标记  0-否  1-是*/
    @ApiModelProperty(value = "是否签收标记  0-否  1-是")
    private Integer isCheck;
    /**物流节点时间*/
    @ApiModelProperty(value = "物流节点时间")
    private String logAt;
    /**createdTime*/
    @ApiModelProperty(value = "createdTime")
    private Date createdTime;
    /**updatedTime*/
    @ApiModelProperty(value = "updatedTime")
    private Date updatedTime;
}
