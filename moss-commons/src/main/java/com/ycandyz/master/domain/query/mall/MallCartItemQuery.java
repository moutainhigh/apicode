package com.ycandyz.master.domain.query.mall;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author: WangYang
 * @Date: 2020/10/13 16:09
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="商城购物车-商品列表")
public class MallCartItemQuery implements Serializable {

    @ApiModelProperty(value = "用户编号")
    private Integer userId;

    @ApiModelProperty(value = "店铺编号")
    private String shopNo;
}
