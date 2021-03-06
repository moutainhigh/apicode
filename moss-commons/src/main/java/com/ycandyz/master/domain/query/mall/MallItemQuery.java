package com.ycandyz.master.domain.query.mall;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ycandyz.master.annotation.Condition;
import com.ycandyz.master.enums.ConditionEnum;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

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

    @ApiModelProperty(value = "商家编号")
    private List<String> shopNoList;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTimeBegin;

    @ApiModelProperty(name = "created_time_end", value = "止")
    @Condition(field = "created_time_end", condition = ConditionEnum.LE)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTimeEnd;

    @ApiModelProperty(name = "is_group", value = "1集团，0非集团")
    @Condition(condition = ConditionEnum.EQ)
    private String isGroup;

    @ApiModelProperty(name = "children_organize_id", value = "集团ID")
    @Condition(condition = ConditionEnum.EQ)
    private String childrenOrganizeId;

    @ApiModelProperty(name = "is_group_supply",value = "是否集团供货(0否,1是)")
    private Integer isGroupSupply;

    @ApiModelProperty(name = "card_id",value = "名片编号")
    private Integer cardId;

    @ApiModelProperty(name = "query_type",value = "查询类型")
    private Integer queryType;

    @ApiModelProperty(name = "card_ids",value = "名片编号（数组查询用）")
    private List<String> cardIds;

    @ApiModelProperty(name = "organize_id",value = "企业编号")
    private Long organizeId;

}
