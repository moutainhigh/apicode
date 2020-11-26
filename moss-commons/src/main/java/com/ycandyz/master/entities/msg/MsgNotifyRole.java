package com.ycandyz.master.entities.msg;

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
 * @Description 业务通知权限表 数据表字段映射类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-13
 * @version 2.0
 */
@Getter
@Setter
@TableName("msg_notify_role")
@ApiModel(description="业务通知权限表")
public class MsgNotifyRole extends Model {


   @ApiModelProperty(value = "业务通知权限表主键")
   @TableId(value = "id", type = IdType.AUTO)
   private Integer id;

   @ApiModelProperty(value = "查看者的权限类型")
   private Integer roleType;

   @ApiModelProperty(value = "查看者权限类型对应的在系统中的角色id")
   private Integer roleId;

   @ApiModelProperty(value = "状态：0正常，1删除")
   private Integer isDel;

   @ApiModelProperty(value = "更新时间")
   private Long updatedAt;

   @ApiModelProperty(value = "创建时间")
   private Long createdAt;


}
