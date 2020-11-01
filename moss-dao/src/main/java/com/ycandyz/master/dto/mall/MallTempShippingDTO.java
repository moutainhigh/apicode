package com.ycandyz.master.dto.mall;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MallTempShippingDTO {

    /**id*/
    @ApiModelProperty(value = "id")
    private Long id;
    /**快递公司编码*/
    @ApiModelProperty(value = "快递公司编码")
    private String comCode;
    /**快递公司名*/
    @ApiModelProperty(value = "快递公司名")
    private String company;
    /**快递单号*/
    @ApiModelProperty(value = "快递单号")
    private String number;
    /**createdAt*/
    @ApiModelProperty(value = "createdAt")
    private Long createdAt;
}
