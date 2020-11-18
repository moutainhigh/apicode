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
 * @Description 业务通知关系表 数据表字段映射类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-13
 * @version 2.0
 */
@Getter
@Setter
@TableName("msg_notify_relation")
@ApiModel(description="业务通知关系表")
public class MsgNotifyRelation extends Model {


   @ApiModelProperty(value = "业务通知关系表主键")
   @TableId(value = "id", type = IdType.AUTO)
   private Integer id;

   @ApiModelProperty(value = "业务通知表主键")
   private Integer msgNotifyId;

   @ApiModelProperty(value = "通知类型：1系统通知，2服务通知")
   private Integer notifyType;

   @ApiModelProperty(value = "查看者的权限类型")
   private Integer roleType;

   @ApiModelProperty(value = "关系类型：1 企业通知 2 代理通知 3有传平台")
   private Integer relationType;

   @ApiModelProperty(value = "关系ID： 对应类型的主键ID，企业类型为organize_id，代理类型为agent_id，有传类型为0")
   private Long relationId;

   @ApiModelProperty(value = "状态：0正常，1删除")
   private Integer isDel;

   @ApiModelProperty(value = "是否已读：0否，1是")
   private Integer isRead;

   @ApiModelProperty(value = "更新时间")
   private Long updatedAt;

   @ApiModelProperty(value = "创建时间")
   private Long createdAt;


}
