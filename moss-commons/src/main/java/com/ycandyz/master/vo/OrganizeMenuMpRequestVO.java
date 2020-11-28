package com.ycandyz.master.vo;

import com.ycandyz.master.entities.miniprogram.OrganizeMallCategoryVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrganizeMenuMpRequestVO {

   @ApiModelProperty(value = "小程序模版id")
   private Integer mpPlanId;

   @ApiModelProperty(value = "menuId,没有草稿或重新选择模版时此字段不用传")
   private Integer menuId;

   @ApiModelProperty(value = "menuName，没有草稿或重新选择模版时此字段必传")
   private String menuName;

   @ApiModelProperty(value = "模块")
   private List<OrganizeMpConfigPageMenuVo> modules;

   @ApiModelProperty(value = "是否重新选择模版[0：否；1：是]")
   private Integer reselectMoudle;

   @ApiModelProperty(value = "一级分类")
   private List<OrganizeMallCategoryVO> imgurls;

}
