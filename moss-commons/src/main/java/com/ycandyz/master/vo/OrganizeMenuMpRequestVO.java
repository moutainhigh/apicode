package com.ycandyz.master.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrganizeMenuMpRequestVO {

   @ApiModelProperty(value = "企业小程序id")
   private Integer id;

   @ApiModelProperty(value = "小程序模版id")
   private Integer mpPlanId;

   @ApiModelProperty(value = "企业小程序名称")
   private String planName;

   @ApiModelProperty(value = "menuId")
   private Integer menuId;

   @ApiModelProperty(value = "menuName")
   private String menuName;

   @ApiModelProperty(value = "模块")
   private List<OrganizeMpConfigPageMenuVo> modules;

   @ApiModelProperty(value = "0：新增；1：修改")
   private Integer flag;


}
