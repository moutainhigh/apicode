package com.ycandyz.master.model.mall;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ycandyz.master.entities.mall.MallCategory;
import lombok.Getter;
import lombok.Setter;

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
public class MallCategoryVO extends MallCategory {

}
