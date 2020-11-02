package com.ycandyz.master.entities.user;

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
 * @Description 权限节点表 数据表字段映射类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-01
 * @version 2.0
 */
@Getter
@Setter
@TableName("node")
@ApiModel(description="权限节点表")
public class Node extends Model {


   @ApiModelProperty(value = "权限节点id")
   @TableId(value = "id", type = IdType.AUTO)
   private Long id;

   @ApiModelProperty(value = "服务端类型(0:Python,1:java,2:Go)")
   private Integer type;

   @ApiModelProperty(value = "0 get/1 post/2 put/3 delete")
   private String methodType;

   @ApiModelProperty(value = "路由地址")
   private String uri;

   @ApiModelProperty(value = "事件")
   private String action;

   @ApiModelProperty(value = "描述")
   private String description;

   @ApiModelProperty(value = "0启用/1停用")
   private Integer status;

   @ApiModelProperty(value = "更新时间")
   private Integer updatedAt;

   @ApiModelProperty(value = "创建时间")
   private Integer createdAt;


}
