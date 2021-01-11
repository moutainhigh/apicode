package com.ycandyz.master.domain.model.mall;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ycandyz.master.validation.ValidatorContract;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Description: 商品分类表
 * @Author sangang
 * @Date 2021/01/8
 * @Version: V1.0
*/
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
public class MallItemDetailModel {

    @ApiModelProperty(name = "item_no",value = "商品编号")
    private String itemNo;

    @NotBlank(message = "商品分类编号不能为空",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    @ApiModelProperty(name = "category_no",value = "商品分类编号")
    private String categoryNo;
}
