package com.ycandyz.master.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrganizeMpRequestVO {

   @ApiModelProperty(value = "所有菜单页面全部数据")
   private List<OrganizeMenuMpRequestVO> organizeMenuMpRequestVOS;

   @ApiModelProperty(value = "状态 [0:草稿；1:发布中]")
   private Integer status;

}
