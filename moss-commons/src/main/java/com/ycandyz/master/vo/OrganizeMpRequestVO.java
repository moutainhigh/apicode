package com.ycandyz.master.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrganizeMpRequestVO {

   @ApiModelProperty(value = "企业小程序id")
   private Integer id;

   @ApiModelProperty(value = "小程序模版id")
   private Integer mpPlanId;

   @ApiModelProperty(value = "企业小程序名称")
   private String planName;

   @ApiModelProperty(value = "所有菜单页面全部数据")
   private List<OrganizeMenuMpVO> allmenus;

   @ApiModelProperty(value = "状态 [0:草稿；1:发布中]")
   private Integer status;

}
