package com.ycandyz.master.entities.mall;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 商城订单表
 * @Author: Wang Yang
 * @Date:   2020-09-19
 * @Version: V1.0
 */
@Data
@TableName("mall_order")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="mall_order对象", description="商城订单表")
public class MallOrder extends Model {
    
	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
	private Integer id;
	/**商店编号*/
    @ApiModelProperty(value = "商店编号")
	private String shopNo;
	/**下单用户id*/
    @ApiModelProperty(value = "下单用户id")
	private Integer userId;
	/**订单编号*/
    @ApiModelProperty(value = "订单编号")
	private String orderNo;
	/**支付方式：0-未知  1-支付宝 2-微信  3-银联*/
    @ApiModelProperty(value = "支付方式：0-未知  1-支付宝 2-微信  3-银联")
	private Integer payType;
	/**交易流水（对外展示的）*/
    @ApiModelProperty(value = "交易流水（对外展示的）")
	private String tradeNo;
	/**交易流水（直接与本系统打交道的平台交易流水，如招行聚合支付，那么这里是招行的交易流水）*/
    @ApiModelProperty(value = "交易流水（直接与本系统打交道的平台交易流水，如招行聚合支付，那么这里是招行的交易流水）")
	private String tradeNoInner;
	/**退款流水（对外展示的）*/
    @ApiModelProperty(value = "退款流水（对外展示的）")
	private String refundNo;
	/**退款流水（直接与本系统打交道的退款流水号，如招行聚合支付，那么这里是招行的退款流水）*/
    @ApiModelProperty(value = "退款流水（直接与本系统打交道的退款流水号，如招行聚合支付，那么这里是招行的退款流水）")
	private String refundNoInner;
	/**支付时间*/
    @ApiModelProperty(value = "支付时间")
	private Integer payedAt;
	/**待付款，买家取消订单原因  501010：多拍/拍错/不想要  501011：不喜欢/效果不好  501012：做工/质量问题  501013：商家发错货  501014：未按约定时间发货  501015：与商品描述严重不符  501016：收到商品少件/破损/污渍  501099：其他
	 待发货，买家取消订单原因 503010-多拍/拍错/不想要  503011-协商一致退款  503012-缺货  503013-未按约定时间发货  503099-其他 待发货，商家取消订单原因 504010-暂停售卖  504011-缺货/断货  504099-其他
	*/
    @ApiModelProperty(value = "待付款，买家取消订单原因  501010：多拍/拍错/不想要  501011：不喜欢/效果不好  501012：做工/质量问题  501013：商家发错货  501014：未按约定时间发货  501015：与商品描述严重不符  501016：收到商品少件/破损/污渍  501099：其他 待发货，买家取消订单原因 503010-多拍/拍错/不想要  503011-协商一致退款  503012-缺货  503013-未按约定时间发货  503099-其他 待发货，商家取消订单原因 504010-暂停售卖  504011-缺货/断货  504099-其他 ")
	private Integer cancelReason;
	/**取消订单时间*/
    @ApiModelProperty(value = "取消订单时间")
	private Integer cancelAt;
	/**售后状态：0-否  1-处理中  2-已完成 */
    @ApiModelProperty(value = "售后状态：0-否  1-处理中  2-已完成 ")
	private Integer afterSalesStatus;
	/**该笔订单售后结束时间*/
    @ApiModelProperty(value = "该笔订单售后结束时间")
	private Integer afterSalesEndAt;
	/**订单金额*/
    @ApiModelProperty(value = "订单金额")
	private java.math.BigDecimal totalMoney;
	/**实际支付金额*/
    @ApiModelProperty(value = "实际支付金额")
	private java.math.BigDecimal realMoney;
	/**总金额（包含real_money和shop_shipping中的运费）*/
    @ApiModelProperty(value = "总金额（包含real_money和shop_shipping中的运费）")
	private java.math.BigDecimal allMoney;
	/**用户下单备注*/
    @ApiModelProperty(value = "用户下单备注")
	private String buyerRemark;
	/**下单时间*/
    @ApiModelProperty(value = "下单时间")
	private Integer orderAt;
	/**10-待支付  20-待发货 30-待收货 40-已收货  50-已取消 */
    @ApiModelProperty(value = "10-待支付  20-待发货 30-待收货 40-已收货  50-已取消 ")
	private Integer status;
	/**1010-初始化 - 待支付  1020-支付失败 - 待支付  1030-支付取消- 待支付 2010-待发货   2020-退款中(并非支付那边的退款中，而是本地的) 2030-无需发货  3010-待收货 4010-系统自动收货  4020-用户手动收货  4030-退款中(并非支付那边的退款中，而是本地的)
  	4040-退款成功  4050-退款关闭 5010-用户主动取消(待支付)  5020-系统超时取消(待支付)  5030-用户主动取消(待发货)  5040-卖家取消(待发货)  6010-用户删除  6020-商家删除*/
    @ApiModelProperty(value = "1010-初始化 - 待支付  1020-支付失败 - 待支付  1030-支付取消- 待支付 2010-待发货   2020-退款中(并非支付那边的退款中，而是本地的) 2030-无需发货  3010-待收货 4010-系统自动收货  4020-用户手动收货  4030-退款中(并非支付那边的退款中，而是本地的) 4040-退款成功  4050-退款关闭 5010-用户主动取消(待支付)  5020-系统超时取消(待支付)  5030-用户主动取消(待发货)  5040-卖家取消(待发货)  6010-用户删除  6020-商家删除")
	private Integer subStatus;
	/**库存扣减方式，10: 拍下减库存，20: 付款减库存*/
    @ApiModelProperty(value = "库存扣减方式，10: 拍下减库存，20: 付款减库存")
	private Integer subStockMethod;
	/**商家发货时间*/
    @ApiModelProperty(value = "商家发货时间")
	private Integer sendAt;
	/**receiveAt*/
    @ApiModelProperty(value = "receiveAt")
	private Integer receiveAt;
	/**买家地址邮政编码*/
    @ApiModelProperty(value = "买家地址邮政编码")
	private Integer receiverRegion;
	/**订单关闭时间*/
    @ApiModelProperty(value = "订单关闭时间")
	private Integer closeAt;
	/**商品订单发票id*/
    @ApiModelProperty(value = "商品订单发票id")
	private Integer mallOrderInvoiceId;
	/**createdTime*/
    @ApiModelProperty(value = "createdTime")
	private java.util.Date createdTime;
	/**updatedTime*/
    @ApiModelProperty(value = "updatedTime")
	private java.util.Date updatedTime;
	/**配送方式，快递: 10, 线下配送：20*/
    @ApiModelProperty(value = "配送方式，快递: 10, 线下配送：20")
	private Integer deliverMethod;
	/**销售员id*/
    @ApiModelProperty(value = "销售员id")
	private Integer sellerUserId;
	/**0-未删除  1-由用户删除  2-由商家删除*/
    @ApiModelProperty(value = "0-未删除  1-由用户删除  2-由商家删除")
	private Integer isDel;
	/**购物车订单编号 */
	@ApiModelProperty(value = "购物车订单编号 ")
	private String cartOrderSn;
	/**购物车订单主键*/
	@ApiModelProperty(value = "购物车订单主键")
	private Long cartOrderId;
	/**订单类型 1->神州通*/
	@ApiModelProperty(value = "订单类型 1->神州通")
	private Integer orderType;
	/**是否集团供货,0-否，1-是*/
	@ApiModelProperty(value = "是否集团供货,0-否，1-是")
	private Integer isGroupSupply;
}
