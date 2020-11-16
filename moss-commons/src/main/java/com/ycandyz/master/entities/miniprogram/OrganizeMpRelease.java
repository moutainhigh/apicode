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
 * @Description 企业小程序发布记录 数据表字段映射类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-16
 * @version 2.0
 */
@Getter
@Setter
@TableName("organize_mp_release")
@ApiModel(description="企业小程序发布记录")
public class OrganizeMpRelease extends Model {


   @ApiModelProperty(value = "编号")
   @TableId(value = "id", type = IdType.AUTO)
   private Integer id;

   @ApiModelProperty(value = "小程序名称")
   private String planName;

   @ApiModelProperty(value = "小程序模板方案编号")
   private Integer planId;

   @ApiModelProperty(value = "申请发布时间")
   private Date createTime;

   @ApiModelProperty(value = "编辑修改时间")
   private Date updateTime;

   @ApiModelProperty(value = "企业类目")
   private String organizeCategory;


}
