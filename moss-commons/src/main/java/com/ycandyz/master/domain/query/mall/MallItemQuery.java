package com.ycandyz.master.domain.query.mall;

import com.ycandyz.master.annotation.Condition;
import com.ycandyz.master.enums.ConditionEnum;

import java.math.BigDecimal;
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
 * @Description 商品表 检索参数类
 * </p>
 *
 * @author SanGang
 * @since 2020-12-19
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="商品表-检索参数")
public class MallItemQuery implements Serializable {

    @ApiModelProperty(name = "shop_no", value = "商店编号")
    @Condition(condition = ConditionEnum.EQ)
    private String shopNo;

    @ApiModelProperty(name = "item_name", value = "商品名称")
    @Condition(condition = ConditionEnum.EQ)
    private String itemName;

    @ApiModelProperty(name = "category_no", value = "分类编号")
    @Condition(condition = ConditionEnum.EQ)
    private String categoryNo;

    @ApiModelProperty(name = "status", value = "10: 上架中，20: 已下架，30: 售罄，40: 仓库中，50: 删除")
    @Condition(condition = ConditionEnum.EQ)
    private Integer status;

    @ApiModelProperty(name = "type", value = "商品类型(0销售商品,1非销售商品)")
    @Condition(condition = ConditionEnum.EQ)
    private Integer type;

    @ApiModelProperty(name = "created_time_begin", value = "起")
    @Condition(field = "created_time_begin", condition = ConditionEnum.GE)
    private Date createdTimeBegin;

    @ApiModelProperty(name = "created_time_end", value = "止")
    @Condition(field = "created_time_end", condition = ConditionEnum.LE)
    private Date createdTimeEnd;


}
