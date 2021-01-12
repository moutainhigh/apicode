package com.ycandyz.master.domain.response.mall;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author WangWx
 * @version 2.0
 * @Description 商品推荐关联表 Resp
 * @since 2021-01-12
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description = "商品推荐关联表-Resp")
public class MallItemRecommendRelationResp implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(name = "item_recommend_setting_no", value = "推荐设置编号")
    private String itemRecommendSettingNo;

    @ApiModelProperty(name = "item_recommend_relation_no", value = "推荐设置关联商品编号")
    private String itemRecommendRelationNo;

    @ApiModelProperty(name = "item_no", value = "商品编号")
    private String itemNo;

    @ApiModelProperty(name = "item_name", value = "商品名称")
    private String itemName;

    @ApiModelProperty(name = "item_cover", value = "封面图")
    private String itemCover;

    @ApiModelProperty(name = "status", value = "状态：1: 正常，0: 无效")
    private Boolean status;

    private Date createdTime;

    private Date updatedTime;


}

