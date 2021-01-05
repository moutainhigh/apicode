package com.ycandyz.master.domain.query.mall;

import com.ycandyz.master.annotation.Condition;
import com.ycandyz.master.enums.ConditionEnum;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * @Description 商品-集团 检索参数类
 * </p>
 *
 * @author SanGang
 * @since 2021-01-05
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="商品-集团-检索参数")
public class MallItemOrganizeQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "id", value = "ID")
    @Condition(field = "id", condition = ConditionEnum.EQ)
    private Long id;

    @ApiModelProperty(name = "shop_no", value = "店铺编号")
    @Condition(condition = ConditionEnum.EQ)
    private String shopNo;

    @ApiModelProperty(name = "organize_item_no", value = "集团商品编号")
    @Condition(condition = ConditionEnum.EQ)
    private String organizeItemNo;

    @ApiModelProperty(name = "item_no", value = "商品编号")
    @Condition(condition = ConditionEnum.EQ)
    private String itemNo;

    @ApiModelProperty(name = "category_no", value = "集团商品分类编号")
    @Condition(condition = ConditionEnum.EQ)
    private String categoryNo;

    @ApiModelProperty(name = "create_time", value = "创建时间起")
    @Condition(field = "create_time", condition = ConditionEnum.GE)
    private Date createTimeS;
    
    @ApiModelProperty(name = "create_time", value = "创建时间止")
    @Condition(field = "create_time", condition = ConditionEnum.LE)
    private Date createTimeE;

    @ApiModelProperty(name = "update_time", value = "更新时间起")
    @Condition(field = "update_time", condition = ConditionEnum.GE)
    private Date updateTimeS;
    
    @ApiModelProperty(name = "update_time", value = "更新时间止")
    @Condition(field = "update_time", condition = ConditionEnum.LE)
    private Date updateTimeE;



}
