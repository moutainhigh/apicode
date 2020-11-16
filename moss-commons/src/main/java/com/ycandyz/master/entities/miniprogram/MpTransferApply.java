package com.ycandyz.master.entities.miniprogram;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 小程序转交接申请 数据表字段映射类
 * </p>
 *
 * @author Wang Yang
 * @since 2020-11-16
 * @version 2.0
 */
@Getter
@Setter
@TableName("mp_transfer_apply")
@ApiModel(description="小程序转交接申请")
public class MpTransferApply extends Model {


   @ApiModelProperty(value = "编号")
   @TableId(value = "id", type = IdType.AUTO)
   private Integer id;

   @ApiModelProperty(value = "企业编号")
   private Integer organizeId;

   @ApiModelProperty(value = "联系人姓名")
   private String contactName;

   @ApiModelProperty(value = "联系人电话")
   private String contactPhone;

   @ApiModelProperty(value = "对接人")
   private String buttedPerson;

   @ApiModelProperty(value = "对接完成时间")
   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
   private Date buttedFinishTime;

   @ApiModelProperty(value = "对接状态0、待对接；1、对接中；2、已完成")
   private Integer buttedStatus;

   @ApiModelProperty(value = "创建时间")
   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
   private Date createTime;

   @ApiModelProperty(value = "修改时间")
   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
   private Date updateTime;


}
