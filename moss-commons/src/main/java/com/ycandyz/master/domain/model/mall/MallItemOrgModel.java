package com.ycandyz.master.domain.model.mall;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ycandyz.master.validation.ValidatorContract;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * @Description 商品信息 Model
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
@ApiModel(description="商品信息-参数")
public class MallItemOrgModel {

    @ApiModelProperty(name = "item_no",value = "商品编号")
    private String itemNo;

    @ApiModelProperty(name = "category_no",value = "分类编号")
    private String categoryNo;

    @ApiModelProperty(name = "is_organize",value = "是否集团供货(0否,1是)")
    private Integer isOrganize;

    @ApiModelProperty(name = "is_all",value = "全部/指定(0全部,1指定)")
    private Integer isAll;

    @ApiModelProperty(name = "shop_nos",value = "店铺编号")
    private List<String> shopNos;

}
