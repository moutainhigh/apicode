package com.ycandyz.master.entities.miniprogram;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 小程序配置-页面配置 数据表字段映射类
 * </p>
 *
 * @author Wang Yang
 * @since 2020-11-15
 * @version 2.0
 */
@Getter
@Setter
@TableName("mp_config_plan_page")
@ApiModel(description="小程序配置-页面配置")
public class MpConfigPlanPage extends Model {


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

   @ApiModelProperty(value = "逻辑删除0：未删除；1、删除")
   private Boolean logicDelete;

   @ApiModelProperty(value = "创建时间")
   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
   private Date createTime;

   @ApiModelProperty(value = "修改时间")
   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
   private Date updateTime;


}
