package com.ycandyz.master.entities.miniprogram;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 企业小程序配置-页面配置 数据表字段映射类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */
@Getter
@Setter
@TableName("organize_mp_config_plan_page")
@ApiModel(description="企业小程序配置-页面配置")
public class OrganizeMpConfigPlanPage extends Model {


   @ApiModelProperty(value = "编号")
   @TableId(value = "id", type = IdType.AUTO)
   private Integer id;

   @ApiModelProperty(value = "菜单编号")
   private Integer menuId;

   @ApiModelProperty(value = "模块编号")
   private Integer moduleId;

   @ApiModelProperty(value = "元素编号")
   private Integer moduleBaseId;

   @ApiModelProperty(value = "展示样式")
   private Integer showLayout;

   @ApiModelProperty(value = "模块排序")
   private Integer sortModule;

   @ApiModelProperty(value = "元素排序")
   private Integer sortBase;

   @ApiModelProperty(value = "元素名称")
   private String baseName;

   @ApiModelProperty(value = "逻辑删除0：未删除，1：删除")
   private Integer logicDelete;

   @ApiModelProperty(value = "一级分类图片")
   private String moudleImgUrl;

}
