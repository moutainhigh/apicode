package com.ycandyz.master.model.mall;

import lombok.*;

import java.math.BigDecimal;

/**
 * 售后详情接口中返回的订单详情表信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MallOrderDetailByAfterSalesVO {

    private String orderNo;
    private String orderDetailNo;
    private String itemNo;
    private String itemName;
    private String itemCover;
    private String goodsNo;
    private String skuNo;
    private Integer quantity;
    private BigDecimal price;
}
