package com.ycandyz.master.domain.response.coupon;

import com.ycandyz.master.entities.coupon.CouponActivity;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

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
@TableName("coupon_activity")
public class CouponActivityResp extends CouponActivity {

    @ApiModelProperty(name = "activity_coupon_list",value = "已选优惠卷")
    private List<CouponActivityCouponResp> activityCouponList;

}
