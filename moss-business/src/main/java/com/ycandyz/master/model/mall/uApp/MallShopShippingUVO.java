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
@ApiModel(value="商家寄出的快递表", description="商家寄出的快递表查询VO")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MallShopShippingUVO {

    @ApiModelProperty(value = "快递公司编码")
    private String code;
    @ApiModelProperty(value = "快递公司名")
    private String company;
    @ApiModelProperty(value = "快递单号")
    private String number;

}
