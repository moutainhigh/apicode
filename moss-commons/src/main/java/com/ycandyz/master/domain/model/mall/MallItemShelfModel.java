package com.ycandyz.master.domain.model.mall;

import com.ycandyz.master.vo.MallSkuVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * <p>
 * @Description 商品上架/下架 Model
 * </p>
 *
 * @author SanGang
 * @since 2020-15-10
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="商品上架/下架-参数")
public class MallItemShelfModel {

    @ApiModelProperty(name = "item_no_list",value = "商品编号列表")
    private String[] itemNoList;

    @ApiModelProperty(value = "10: 上架中，20: 已下架，30: 售罄，40: 仓库中，50: 删除")
    private Integer status;

}
