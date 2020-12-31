package com.ycandyz.master.domain.model.mall;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.io.Serializable;

/**
 * <p>
 * @Description sku值表 Model
 * </p>
 *
 * @author SanGang
 * @since 2020-12-20
 * @version 2.0
 */
@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description="sku值表-Model")
public class MallSkuSpecModel implements Serializable {

   private static final long serialVersionUID = 1L;

   private Long id;

   @ApiModelProperty(name = "sku_no", value = "sku编号")
   private String skuNo;

   @ApiModelProperty(name = "spec_name", value = "名称")
   private String specName;

   @ApiModelProperty(name = "spec_value", value = "值")
   private String specValue;

   private Date createdTime;

   private Date updatedTime;


}

