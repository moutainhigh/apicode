package com.ycandyz.master.entities.coupon;

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
 * @Description 发卷宝 数据表字段映射类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */
@Getter
@Setter
@TableName("coupon_activity")
@ApiModel(description="发卷宝")
public class CouponActivity extends Model {


   @TableId(value = "id", type = IdType.AUTO)
   private Long id;

   @ApiModelProperty(value = "活动编号")
   private String activityNo;

   @ApiModelProperty(value = "门店编号")
   private String shopNo;

   @ApiModelProperty(value = "活动名称")
   private String name;

   @ApiModelProperty(value = "活动开始时间")
   private Long beginAt;

   @ApiModelProperty(value = "活动结束时间")
   private Long endAt;

   @ApiModelProperty(value = "赠送上限人数")
   private Integer heightLimit;

   @ApiModelProperty(value = "活动状态：0:未开始，1:进行中，2:已结束，3:已停止")
   private Integer status;

   @ApiModelProperty(value = "活动参与人类型：0:全部用户，1:仅老用户，2:仅新用户")
   private Integer joinType;

   @ApiModelProperty(value = "活动参与人数")
   private Integer activityNum;

   @ApiModelProperty(value = "活动添加时间")
   private Long createdAt;

   @ApiModelProperty(value = "活动修改时间")
   private Long updatedAt;

   @ApiModelProperty(value = "创建人")
   private Long createdBy;

   @ApiModelProperty(value = "更新人")
   private Long updatedBy;


}
