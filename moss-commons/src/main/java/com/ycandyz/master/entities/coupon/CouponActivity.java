package com.ycandyz.master.entities.coupon;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ycandyz.master.validation.ValidatorContract;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@TableName("coupon_activity")
@ApiModel(description="发卷宝")
public class CouponActivity extends Model {


   @TableId(value = "id", type = IdType.AUTO)
   private Long id;

   @ApiModelProperty(name = "activity_no",value = "活动编号")
   private String activityNo;

   @ApiModelProperty(name = "shop_no",value = "门店编号")
   private String shopNo;

   @Size(max = 20, message = "活动名称不能大于20个字。",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
   @NotNull(message = "活动名称不能为空",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
   @ApiModelProperty(value = "活动名称")
   private String name;

   @Size(max = 4, message = "活动入口名称不能大于4个字。",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
   @NotNull(message = "活动入口名称不能为空",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
   @ApiModelProperty(name = "join_name",value = "活动入口名称")
   private String joinName;

   @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
   @NotNull(message = "活动开始时间不能为空",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
   @ApiModelProperty(name = "begin_time",value = "活动开始时间")
   private Date beginTime;

   @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
   @NotNull(message = "活动结束时间不能为空",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
   @ApiModelProperty(name = "end_time",value = "活动结束时间")
   private Date endTime;

   @Range(min = 1, max = 1000000, message = "赠送上限人数范围为1-1000000之间", groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
   @NotNull(message = "活赠送上限人数不能为空",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
   @ApiModelProperty(name = "height_limit",value = "赠送上限人数")
   private Integer heightLimit;

   @ApiModelProperty(value = "活动状态：0:默认，1:未开始，2:进行中，3:已结束，4:已停止")
   private Integer status;

   @Range(min = 0, max = 2, message = "活动参与人群类型必须为0-2之间", groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
   @NotNull(message = "活动参与人群不能为空",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
   @ApiModelProperty(name = "join_type",value = "活动参与人群：0:全部用户，1:仅老用户，2:仅新用户")
   private Integer joinType;

   @Range(min = 0, max = 2, message = "活页面设置必须为0-2之间", groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
   @ApiModelProperty(name = "page_type",value = "页面设置：0店铺页面")
   private Integer pageType;

   @ApiModelProperty(name = "activity_num",value = "活动参与人数")
   private Integer activityNum;

   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
   @ApiModelProperty(name = "create_time",value = "创建时间")
   private Date createTime;

   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
   @ApiModelProperty(name = "update_time",value = "更新时间")
   private Date updateTime;

   @ApiModelProperty(name = "create_by",value = "创建人")
   private Long createBy;

   @ApiModelProperty(name = "update_by",value = "更新人")
   private Long updateBy;


}
