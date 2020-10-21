package com.ycandyz.master.model.mall;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MallOrderDetailSpecVO {

    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "商品详情编号")
    private String orderDetailNo;
    @ApiModelProperty(value = "规格名称")
    private String specName;
    @ApiModelProperty(value = "规格值")
    private String specValue;
    @ApiModelProperty(value = "createdTime")
    private Date createdTime;
    @ApiModelProperty(value = "updatedTime")
    private Date updatedTime;
}
