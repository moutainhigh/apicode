package com.ycandyz.master.model.mall;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ycandyz.master.entities.mall.MallCategory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <p>
 * @Description 商城 - 商品分类表 VO
 * </p>
 *
 * @author SanGang
 * @since 2020-10-15
 * @version 2.0
 */
@Getter
@Setter
@TableName("mall_category")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MallCategoryVO extends MallCategory {

    @ApiModelProperty(value = "子集数组")
    private List<MallCategoryVO> chaildList;
}
