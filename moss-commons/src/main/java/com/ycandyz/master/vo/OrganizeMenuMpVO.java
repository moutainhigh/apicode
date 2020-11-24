package com.ycandyz.master.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrganizeMenuMpVO {

   @ApiModelProperty(value = "menuId")
   private Integer menuId;

   @ApiModelProperty(value = "menuName")
   private String menuName;

   @ApiModelProperty(value = "模块")
   private List<OrganizeMpConfigPageMenuVo> modules;

   @ApiModelProperty(value = "是否删除[0：否；1：是]")
   private Integer isDel;

}
