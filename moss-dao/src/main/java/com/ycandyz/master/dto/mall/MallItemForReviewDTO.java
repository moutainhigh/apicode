package com.ycandyz.master.dto.mall;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * @Description 商品表 商品详细
 * </p>
 *
 * @author SanGang
 * @since 2020-10-23
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="商品表-商品详细")
public class MallItemForReviewDTO implements Serializable {

    @ApiModelProperty(value = "商品名称")
    private String itemName;

    @ApiModelProperty(value = "商品描述")
    private String itemText;

    @ApiModelProperty(value = "轮播图，jsonarray")
    private String banners;

    @ApiModelProperty(value = "分享描述")
    private String shareDescr;

    @ApiModelProperty(value = "分享图片")
    private String shareImg;
}
