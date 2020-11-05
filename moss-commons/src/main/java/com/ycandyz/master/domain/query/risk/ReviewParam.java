package com.ycandyz.master.domain.query.risk;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReviewParam {

    @ApiModelProperty(value = "内容id 商友圈id/商品详情Id/企业动态id")
    private Long contentId;

    @ApiModelProperty(value = "内容模块[0:商品详情(表:mall_item);1:商友圈(表:footprint);2:企业动态(表:organize_news)]")
    private Integer type;

    @ApiModelProperty(value = "审核操作 mall_item[60：系统屏蔽 20: 已下架] footprint[是否删除 0-否  2-系统屏蔽] organize_news[是否删除 0-正常 2-系统屏蔽]")
    private Integer oper;

    @ApiModelProperty(value = "mall_item status字段值 10: 上架中，20: 已下架，30: 售罄，40: 仓库中，50: 删除，60：系统屏蔽")
    private Integer status;


}
