package com.ycandyz.master.domain.model.mall;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * <p>
 * @Description 商品SKuSpec Model
 * </p>
 *
 * @author SanGang
 * @since 2020-12-18
 * @version 2.0
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="商品SkuSpec-参数")
public class MallItemSkuSpecModel {

    @ApiModelProperty(name = "spec_name",value = "规格名称")
    private String specName;

    @ApiModelProperty(name = "spec_value",value = "规格值")
    private String specValue;

    @ApiModelProperty(name = "exist_img",value = "图片标识(0不存在,1存在)")
    private Integer existImg;

}
