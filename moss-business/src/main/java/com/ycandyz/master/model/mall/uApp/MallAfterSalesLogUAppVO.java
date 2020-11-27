package com.ycandyz.master.model.mall.uApp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ApiModel(value="售后沟通日志表", description="售后沟通日志表查询VO")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MallAfterSalesLogUAppVO {

    @ApiModelProperty(value = "id")
    private Long id;
    /**订单编号*/
    @ApiModelProperty(value = "订单详情售后编号")
    private String afterSalesNo;
    /**订单编号*/
    @ApiModelProperty(value = "店铺编号")
    private String shopNo;
    /**订单编号*/
    @ApiModelProperty(value = "用户id")
    private Long userId;
    /**订单编号*/
    @ApiModelProperty(value = "订单详情售后沟通日志编号")
    private String logNo;
    /**订单编号*/
    @ApiModelProperty(value = "商家视角的内容")
    private String contextShop;
    /**订单编号*/
    @ApiModelProperty(value = "买家视角的内容")
    private String contextBuyer;
    /**订单编号*/
    @ApiModelProperty(value = "createdTime")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createdTime;
    /**订单编号*/
    @ApiModelProperty(value = "updatedTime")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;
}
