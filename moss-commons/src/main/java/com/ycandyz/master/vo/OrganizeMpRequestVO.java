package com.ycandyz.master.vo;

import com.ycandyz.master.entities.miniprogram.OrganizeMallCategoryVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrganizeMpRequestVO {

   @ApiModelProperty(value = "小程序模版id")
   private Integer mpPlanId;

   @ApiModelProperty(value = "所有菜单页面全部数据")
   private List<OrganizeMenuMpVO> allmenus;

   @ApiModelProperty(value = "状态 [0:草稿；1:发布成功]")
   private Integer status;

   @ApiModelProperty(value = "一级分类")
   private List<OrganizeMallCategoryVO> imgurls;

}
