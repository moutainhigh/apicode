package com.ycandyz.master.domain.response.mall;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description 传播商品分页 Resp
 * @author WangWx
 * @since 2021-01-11
 * @version 2.0
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description="传播商品表-Resp")
public class SpreadMallItemShopInfoResp {

   private static final long serialVersionUID = 1L;

   @ApiModelProperty(name = "item_no", value = "商品编号")
   private String itemNo;

   @ApiModelProperty(name = "shop_no", value = "门店编号")
   private String shopNo;

   @ApiModelProperty(name = "shop_name", value = "门店编号")
   private String shopName;

   @ApiModelProperty(name = "status", value = "门店状态")
   private Integer status;

   @ApiModelProperty(name = "operate_status", value = "商店经营状态")
   private Integer operateStatus;

}

