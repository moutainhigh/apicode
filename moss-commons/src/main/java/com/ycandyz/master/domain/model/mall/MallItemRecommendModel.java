package com.ycandyz.master.domain.model.mall;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ycandyz.master.domain.response.mall.MallItemRecommendRelationResp;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author WangWx
 * @version 2.0
 * @Description 商品推荐基础表 Model
 * @since 2021-01-12
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description = "商品推荐基础表-Model")
public class MallItemRecommendModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(name = "item_recommend_no", value = "推荐编号")
    private String itemRecommendNo;

    @ApiModelProperty(name = "location_name", value = "推荐位置名称")
    private String locationName;

    @ApiModelProperty(name = "is_recommend", value = "默认是否推荐 0不推荐 1推荐")
    private Integer isRecommend;

    @ApiModelProperty(name = "recommend_way", value = "默认推荐方式 1自动推荐 2手动选择")
    private Integer recommendWay;

    @ApiModelProperty(name = "recommend_type", value = "自动推荐类型 1 最新添加的商品 2 全部商品浏览TOP 3 全部商品销售TOP 4 全部商品排序值TOP")
    private Integer recommendType;

    @ApiModelProperty(name = "show_num", value = "展示的数量")
    private Integer showNum;

    @ApiModelProperty(name = "status", value = "0-无效   1-有效")
    private Integer status;

    @ApiModelProperty(name = "show_name", value = "展示名称")
    private String showName;

    @ApiModelProperty(name = "recommend_item_list", value = "商品信息")
    private List<String> recommendItemList;

    private Date createdTime;

    private Date updatedTime;


}

