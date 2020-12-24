package com.ycandyz.master.domain.model.coupon;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ycandyz.master.entities.coupon.CouponActivity;
import com.ycandyz.master.validation.ValidatorContract;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * @Description 发卷宝 Resp
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */
@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description="发卷宝-参数")
public class CouponActivityModel {


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Size(max = 40, message = "活动名称不能大于20个字。",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    @NotBlank(message = "请输入活动名称",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    @ApiModelProperty(value = "活动名称", required = true)
    private String title;

    @Size(max = 8, message = "活动入口名称不能大于4个字。",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    @NotBlank(message = "活动入口名称不能为空",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    @ApiModelProperty(name = "inlet_name",value = "活动入口名称", required = true)
    private String inletName;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "活动开始时间不能为空",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    @ApiModelProperty(name = "begin_time",value = "活动开始时间", required = true)
    private Date beginTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "活动结束时间不能为空",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    @ApiModelProperty(name = "end_time",value = "活动结束时间", required = true)
    private Date endTime;

    @Range(min = 1, max = 1000000, message = "赠送上限人数范围为1-1000000之间", groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    @NotNull(message = "活赠送上限人数不能为空",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    @ApiModelProperty(name = "max_limit",value = "赠送上限人数", required = true)
    private Integer maxLimit;

    @Range(min = 0, max = 2, message = "活动参与人类型必须为0-2之间", groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    @NotNull(message = "活动参与人不能为空",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    @ApiModelProperty(name = "user_type",value = "活动参与人群：0:全部用户，1:仅老用户，2:仅新用户", required = true)
    private Integer userType;

    @Range(min = 0, max = 2, message = "活页面设置必须为0-2之间", groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    @ApiModelProperty(name = "page_type",value = "页面设置：0店铺页面", required = true)
    private Integer pageType;

    @NotEmpty(message = "请选择优惠券",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    @ApiModelProperty(name = "coupon_ids",value = "优惠卷ID", required = true)
    private List<Long> couponIds;


}
