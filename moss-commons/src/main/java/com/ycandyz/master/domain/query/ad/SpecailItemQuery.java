package com.ycandyz.master.domain.query.ad;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * @Description 专题-商品表 检索参数类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-23
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="专题-商品表-检索参数")
public class SpecailItemQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商店编号")
    private String shopNo;

    @ApiModelProperty(value = "专题编号")
    private String specialNo;


}
