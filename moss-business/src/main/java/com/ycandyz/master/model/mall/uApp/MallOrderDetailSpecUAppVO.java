package com.ycandyz.master.model.mall.uApp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MallOrderDetailSpecUAppVO {

    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "商品详情编号")
    private String orderDetailNo;
    @ApiModelProperty(value = "规格名称")
    private String specName;
    @ApiModelProperty(value = "规格值")
    private String specValue;
    @ApiModelProperty(value = "createdTime")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createdTime;
    @ApiModelProperty(value = "updatedTime")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date updatedTime;
}
