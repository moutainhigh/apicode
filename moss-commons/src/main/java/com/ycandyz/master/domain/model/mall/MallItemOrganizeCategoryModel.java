package com.ycandyz.master.domain.model.mall;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ycandyz.master.entities.mall.MallCategory;
import com.ycandyz.master.entities.mall.MallItemOrganize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * @Description 集团供货分类与商品批量新增Model
 * </p>
 *
 * @author SanGang
 * @since 2021-01-05
 * @version 2.0
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description="集团供货分类与商品批量新增Model")
public class MallItemOrganizeCategoryModel implements Serializable {

   private static final long serialVersionUID = 1L;

   List<MallCategory> mclist = new ArrayList<>();

   List<MallItemOrganize> addIolist = new ArrayList<>();

   List<MallItemOrganize> updateIolist = new ArrayList<>();


}

