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
 * @Description 企业小程序配置方案 数据表字段映射类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */
@Getter
@Setter
@TableName("organize_mp_config_plan")
@ApiModel(description="企业小程序配置方案")
public class OrganizeMpConfigPlan extends Model {


   @ApiModelProperty(value = "编号")
   @TableId(value = "id", type = IdType.AUTO)
   private Integer id;

   @ApiModelProperty(value = "企业编号")
   private Long organizeId;

   @ApiModelProperty(value = "小程序模板方案编号")
   private Integer mpPlanId;

   @ApiModelProperty(value = "逻辑删除0：未删除；1、删除")
   private Integer logicDelete;

   @ApiModelProperty(value = "当前应用0：未应用；1、应用")
   private Integer currentUsing;

   @ApiModelProperty(value = "创建时间")
   private Date createTime;

   @ApiModelProperty(value = "修改时间")
   private Date updateTime;

}
