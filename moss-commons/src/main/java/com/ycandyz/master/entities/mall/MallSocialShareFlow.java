package com.ycandyz.master.entities.mall;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@TableName("mall_social_share_flow")
@ApiModel(description="商城 - 佣金流水表")
public class MallSocialShareFlow extends Model {

    /**id*/
    @ApiModelProperty(value = "id")
    private Long id;
    /**佣金流水号*/
    @ApiModelProperty(value = "佣金流水号")
    private String shareBillNo;
    /**用户id*/
    @ApiModelProperty(value = "用户id")
    private Long userId;
    /**用户名称*/
    @ApiModelProperty(value = "用户名称")
    private String userName;
    /**电话号码*/
    @ApiModelProperty(value = "电话号码")
    private String phoneNo;
    /**企业id*/
    @ApiModelProperty(value = "企业id")
    private Integer organizeId;
    /**企业名称*/
    @ApiModelProperty(value = "企业名称")
    private String organizeName;
    /**分佣金额*/
    @ApiModelProperty(value = "分佣金额")
    private BigDecimal amount;
    /**关联清单支付: xxx元; 分佣：xxx元；退款：xxx元；*/
    @ApiModelProperty(value = "关联清单支付: xxx元; 分佣：xxx元；退款：xxx元；")
    private String relationInfo;
    /**关联订单号*/
    @ApiModelProperty(value = "关联订单号")
    private String orderNo;
    /**订单创建时间*/
    @ApiModelProperty(value = "订单创建时间")
    private Integer orderCreateTime;
    /**银行流水号*/
    @ApiModelProperty(value = "银行流水号")
    private String bankBillNo;
    /**银行流水时间*/
    @ApiModelProperty(value = "银行流水时间")
    private String bankBillTime;
    /**佣金流水状态10: 未入账，20: 已入账，30: 退回*/
    @ApiModelProperty(value = "佣金流水状态10: 未入账，20: 已入账，30: 退回")
    private Integer status;
    /**created_time*/
    @ApiModelProperty(value = "created_time")
    private Integer createdTime;
    /**updatedTime*/
    @ApiModelProperty(value = "updatedTime")
    private Integer updatedTime;
    /**佣金类型 0:分销佣金 1管理佣金*/
    @ApiModelProperty(value = "佣金类型 0:分销佣金 1管理佣金")
    private Integer shareType;
    /**合伙人的历史等级 1团长 2达人 3掌柜*/
    @ApiModelProperty(value = "合伙人的历史等级 1团长 2达人 3掌柜")
    private Integer level;
}
