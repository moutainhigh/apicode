package com.ycandyz.master.entities.miniprogram;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 企业小程序配置-菜单配置 数据表字段映射类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */
@Getter
@Setter
@TableName("organize_mp_config_plan_menu")
@ApiModel(description="企业小程序配置-菜单配置")
public class OrganizeMpConfigPlanMenu extends Model {


   @ApiModelProperty(value = "编号")
   @TableId(value = "id", type = IdType.AUTO)
   private Integer id;

   @ApiModelProperty(value = "名称")
   private String title;

   @ApiModelProperty(value = "选中图片地址")
   private String pictureSelect;

   @ApiModelProperty(value = "选中颜色")
   private String colorSelect;

   @ApiModelProperty(value = "未选中图片地址")
   private String pictureNotSelect;

   @ApiModelProperty(value = "未选中颜色")
   private String colorNotSelect;

   @ApiModelProperty(value = "排序值")
   private Integer sortNum;

   @ApiModelProperty(value = "企业小程序方案编号")
   private Integer organizePlanId;

   @ApiModelProperty(value = "逻辑删除0：未删除；1、删除")
   private Boolean logicDelete;

   @ApiModelProperty(value = "是否可布局0：不可布局；1、可布局")
   private Boolean canLayout;

   @ApiModelProperty(value = "是否可删除0：不可删除，1：可删除")
   private Boolean canDelete;

   @ApiModelProperty(value = "创建时间")
   private Date createTime;

   @ApiModelProperty(value = "修改时间")
   private Date updateTime;


}
