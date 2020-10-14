package com.ycandyz.master.entities.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: U客订单表
 * @Author: Wang Yang
 * @Date:   2020-09-20
 * @Version: V1.0
 */
@Data
@TableName("order")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="order对象", description="U客订单表")
public class Order {
    
	/**自增主键*/
	@TableId(type = IdType.UUID)
    @ApiModelProperty(value = "自增主键")
	private Integer id;
	/**实际支付订单号*/
    @ApiModelProperty(value = "实际支付订单号")
	private String payOrderNo;
	/**订单编号*/
    @ApiModelProperty(value = "订单编号")
	private String orderNo;
	/**组织id*/
    @ApiModelProperty(value = "组织id")
	private Integer organizeId;
	/**用户id*/
    @ApiModelProperty(value = "用户id")
	private Integer userId;
	/**总价格*/
    @ApiModelProperty(value = "总价格")
	private java.math.BigDecimal totalMoney;
	/**真实价格*/
    @ApiModelProperty(value = "真实价格")
	private java.math.BigDecimal realMoney;
	/**是否线上支付  0-否  1-是*/
    @ApiModelProperty(value = "是否线上支付  0-否  1-是")
	private Integer payOnline;
	/**支付方式：0-未知 1-支付宝 2-微信 3-银行转账 4-代理商代付*/
    @ApiModelProperty(value = "支付方式：0-未知 1-支付宝 2-微信 3-银行转账 4-代理商代付")
	private Integer payType;
	/**是否免费  0-否  1-是  默认0*/
    @ApiModelProperty(value = "是否免费  0-否  1-是  默认0")
	private Integer isFree;
	/**支付状态：0-待支付 1-支付失败 2-支付成功 3-取消支付 4-待收款*/
    @ApiModelProperty(value = "支付状态：0-待支付 1-支付失败 2-支付成功 3-取消支付 4-待收款")
	private Integer payStatus;
	/**支付备注*/
    @ApiModelProperty(value = "支付备注")
	private String payRemark;
	/**审核状态： 0-审核中 1-审核失败 2-审核成功*/
    @ApiModelProperty(value = "审核状态： 0-审核中 1-审核失败 2-审核成功")
	private Integer auditStatus;
	/**支付时间*/
    @ApiModelProperty(value = "支付时间")
	private Integer payTime;
	/**审核时间*/
    @ApiModelProperty(value = "审核时间")
	private Integer auditTime;
	/**审核用户id*/
    @ApiModelProperty(value = "审核用户id")
	private Integer auditUserId;
	/**审核备注*/
    @ApiModelProperty(value = "审核备注")
	private String auditRemark;
	/**支付流水*/
    @ApiModelProperty(value = "支付流水")
	private String tradeNo;
	/**汇款户名*/
    @ApiModelProperty(value = "汇款户名")
	private String remitAccountName;
	/**汇款账号*/
    @ApiModelProperty(value = "汇款账号")
	private String remitAccountNo;
	/**汇款日期*/
    @ApiModelProperty(value = "汇款日期")
	private Integer remitDate;
	/**汇款凭证*/
    @ApiModelProperty(value = "汇款凭证")
	private String remitVoucher;
	/**汇款备注*/
    @ApiModelProperty(value = "汇款备注")
	private String remitRemark;
	/**收款凭证*/
    @ApiModelProperty(value = "收款凭证")
	private String receiptVoucher;
	/**是否开票 0-未开票 1-开票中 2-已开票*/
    @ApiModelProperty(value = "是否开票 0-未开票 1-开票中 2-已开票")
	private Integer isInvoice;
	/**是否删除 0-正常 1-已删除*/
    @ApiModelProperty(value = "是否删除 0-正常 1-已删除")
	private Integer isDel;
	/**创建时间*/
    @ApiModelProperty(value = "创建时间")
	private Integer createdAt;
	/**更新时间*/
    @ApiModelProperty(value = "更新时间")
	private Integer updatedAt;
	/**代理商id*/
    @ApiModelProperty(value = "代理商id")
	private Integer organizeFatherId;
	/**代理成员id*/
    @ApiModelProperty(value = "代理成员id")
	private Integer organizeInviteEmployeeId;
	/**代理普通用户id*/
    @ApiModelProperty(value = "代理普通用户id")
	private Integer organizeInviteUserId;
	/**分润规则id*/
    @ApiModelProperty(value = "分润规则id")
	private Integer series;
	/**默认 0 0未计算分润/1已计算分润*/
    @ApiModelProperty(value = "默认 0 0未计算分润/1已计算分润")
	private Integer profitIsDeal;
	/**来源 1-商品 2-活动*/
    @ApiModelProperty(value = "来源 1-商品 2-活动")
	private Integer sourceFrom;
	/**来源详情 （商品内容）商品类型 0-未知 1-服务  2-名片  3-空内容 4-活动服务 5-活动名片 6-活动服务名片 7-活动无内容 */
    @ApiModelProperty(value = "来源详情 （商品内容）商品类型 0-未知 1-服务  2-名片  3-空内容 4-活动服务 5-活动名片 6-活动服务名片 7-活动无内容 ")
	private Integer sourceDetail;
	/**使用方式 1-自用 2-预存*/
    @ApiModelProperty(value = "使用方式 1-自用 2-预存")
	private Integer useType;
}
