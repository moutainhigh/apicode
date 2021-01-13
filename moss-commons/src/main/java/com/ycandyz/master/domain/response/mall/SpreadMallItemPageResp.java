package com.ycandyz.master.domain.response.mall;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ycandyz.master.api.BasePageResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author WangWx
 * @version 2.0
 * @Description 传播商品分页 Resp
 * @since 2021-01-11
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description = "传播商品表-Resp")
public class SpreadMallItemPageResp {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "res", value = "商品信息")
    private BasePageResult<Page<SpreadMallItemPageRespInfo>> res;

    @ApiModelProperty(name = "shop_info", value = "门店信息")
    private SpreadMallItemShopInfoResp shopInfo;
}

