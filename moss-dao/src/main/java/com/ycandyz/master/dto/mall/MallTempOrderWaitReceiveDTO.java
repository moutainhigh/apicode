package com.ycandyz.master.dto.mall;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MallTempOrderWaitReceiveDTO {

    @ApiModelProperty(value = "id")
    private Long id;
    /**临时订单编号*/
    @ApiModelProperty(value = "临时订单编号")
    private String tempOrderNo;
    /**订单编号*/
    @ApiModelProperty(value = "订单编号")
    private String orderNo;
    /**店铺编号*/
    @ApiModelProperty(value = "店铺编号")
    private String shopNo;
    /**店铺编号*/
    @ApiModelProperty(value = "订单将要关闭的时间")
    private Long closeAt;
    /**createdTime*/
    @ApiModelProperty(value = "createdTime")
    private Date createdTime;
    /**updatedTime*/
    @ApiModelProperty(value = "updatedTime")
    private Date updatedTime;
}
