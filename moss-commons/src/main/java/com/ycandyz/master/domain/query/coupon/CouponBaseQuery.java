package com.ycandyz.master.domain.query.coupon;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * @Description 优惠卷 检索参数类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description="优惠卷-检索参数")
public class CouponBaseQuery implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(name = "id",value = "优惠券id")
    private Long id;
    @ApiModelProperty(name = "type",value = "类型：all-查询全部，choose-查询选中")
    private String type;
    @ApiModelProperty(name = "keyword",value = "关键词")
    private String keyword;
    @ApiModelProperty(name = "category",value = "多级分类，逗号拼接")
    private String categoryNo;

}
