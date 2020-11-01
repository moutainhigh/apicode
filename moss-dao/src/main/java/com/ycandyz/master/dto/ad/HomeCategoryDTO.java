package com.ycandyz.master.dto.ad;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ycandyz.master.entities.ad.HomeCategory;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <p>
 * @Description 首页- 轮播图分类表 实体类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-15
 * @version 2.0
 */
@Getter
@Setter
@TableName("ad_home_category")
public class HomeCategoryDTO extends HomeCategory {

    @ApiParam(hidden = true)
    @TableField(exist = false)
    @ApiModelProperty(value = "是否选中(true选择,false未选择)")
    private Boolean selected;

    @ApiModelProperty(value = "页面实体集合")
    private List<HomeCategoryDTO> homeCategoryList;

    @ApiModelProperty(value = "页面实体")
    private HomeCategoryDTO homeCategory;



}
