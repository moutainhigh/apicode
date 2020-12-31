package com.ycandyz.master.domain.model.mall;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ycandyz.master.entities.mall.MallSkuSpec;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
public class MallSpecsModel implements Serializable {

   @ApiModelProperty(name = "spec_name", value = "名称")
   private String specName;

   @ApiModelProperty(name = "spec_list",value = "规格值列表")
   private List<MallSkuSpec> specList;


}

