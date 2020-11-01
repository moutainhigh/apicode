package com.ycandyz.master.model.ad;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ycandyz.master.entities.ad.HomeCategory;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 首页- 轮播图分类表 VO
 * </p>
 *
 * @author SanGang
 * @since 2020-10-15
 * @version 2.0
 */
@Getter
@Setter
@TableName("ad_home_category")
public class HomeCategoryVO extends HomeCategory {

}
