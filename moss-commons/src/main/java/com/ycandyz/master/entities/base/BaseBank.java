package com.ycandyz.master.entities.base;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description base-银行 数据表字段映射类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-04
 * @version 2.0
 */
@Getter
@Setter
@TableName("base_bank")
@ApiModel(description="base-银行")
public class BaseBank extends Model {


   @ApiModelProperty(value = "ID")
   @TableId(value = "id", type = IdType.AUTO)
   private Long id;

   @ApiModelProperty(value = "名称")
   private String name;

   @ApiModelProperty(value = "编号")
   private String code;

   @ApiModelProperty(value = "排序")
   private Integer sort;

   @ApiModelProperty(value = "删除标识(0未删除,1已删除)")
   @TableLogic
   private Integer deleted;

   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
   @ApiModelProperty(value = "创建时间")
   private Date createdTime;

   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
   @ApiModelProperty(value = "更新时间")
   private Date updatedTime;


}
