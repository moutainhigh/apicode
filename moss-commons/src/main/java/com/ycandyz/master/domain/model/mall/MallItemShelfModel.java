package com.ycandyz.master.domain.model.mall;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ycandyz.master.validation.ValidatorContract;
import com.ycandyz.master.vo.MallSkuVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
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
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="商品上架/下架-参数")
public class MallItemShelfModel {

    @ApiModelProperty(name = "item_no_list",value = "商品编号")
    @NotEmpty(message = "商品编号不能为空",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    private String[] itemNoList;

    @ApiModelProperty(value = "10: 上架中，20: 已下架，30: 售罄，40: 仓库中，50: 删除")
    private Integer status;

}
