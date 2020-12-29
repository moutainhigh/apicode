package com.ycandyz.master.domain.model.mall;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
 import lombok.Data;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.io.Serializable;

/**
 * <p>
 * @Description 分销设置表 Model
 * </p>
 *
 * @author SanGang
 * @since 2020-12-29
 * @version 2.0
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description="分销设置表-Model")
public class MallSocialSettingModel implements Serializable {

   private static final long serialVersionUID = 1L;

   private Long id;

   @ApiModelProperty(name = "shop_no", value = "商店编号")
   private String shopNo;

   @ApiModelProperty(name = "organize_id", value = "企业id")
   private Integer organizeId;

   @ApiModelProperty(name = "is_default_item", value = "商品默认参与 0:不参与，1:参与")
   private Boolean isDefaultItem;

   @ApiModelProperty(name = "withdraw_method", value = "提现方式10:线上自动打")
   private Boolean withdrawMethod;

   @ApiModelProperty(name = "withdraw_lowest_amount", value = "单次提现最小额度")
   private BigDecimal withdrawLowestAmount;

   @ApiModelProperty(name = "withdraw_limit_cycle", value = "提现限制周期0:按日 1:按周 2:按月")
   private Boolean withdrawLimitCycle;

   @ApiModelProperty(name = "withdraw_limit_times", value = "提现限制")
   private Integer withdrawLimitTimes;

   @ApiModelProperty(name = "tax_rate", value = "代缴税比例")
   private BigDecimal taxRate;

   @ApiModelProperty(name = "created_time", value = "创建时间")
   private Date createdTime;

   @ApiModelProperty(name = "updated_time", value = "修改时间")
   private Date updatedTime;

   @ApiModelProperty(name = "bank_no", value = "银行账号")
   private String bankNo;

   @ApiModelProperty(name = "account_bank", value = "银行名称")
   private String accountBank;

   @ApiModelProperty(name = "account_name", value = "账户名称")
   private String accountName;

   @ApiModelProperty(name = "settle_cycle", value = "结算周期10:自然月 20:自然周 30:自然日")
   private Boolean settleCycle;

   @ApiModelProperty(name = "account_province", value = "账户所属省份")
   private String accountProvince;

   @ApiModelProperty(name = "account_city", value = "账户所属城市")
   private String accountCity;

   @ApiModelProperty(name = "account_bank_branch", value = "银行支行")
   private String accountBankBranch;

   @ApiModelProperty(name = "share_standard", value = "定义标准 10:首次访问，20:下单，30.支付")
   private Boolean shareStandard;

   @ApiModelProperty(name = "is_enable", value = "是否开启分销:0:否 1:是")
   private Boolean isEnable;

   @ApiModelProperty(name = "share_level_type", value = "分销层级 0未开启 1 一层 2 三层 3二层")
   private Integer shareLevelType;

   @ApiModelProperty(name = "share_whole_method", value = "分销方式 1基于等级 2基于推荐链")
   private Integer shareWholeMethod;

   @ApiModelProperty(name = "share_method", value = "U团长分销佣金的方式0:按比例 1:按金额")
   private Boolean shareMethod;

   @ApiModelProperty(name = "share_rate", value = "U团长分销佣金按比例")
   private BigDecimal shareRate;

   @ApiModelProperty(name = "share_amount", value = "U团长分销佣金按金额")
   private BigDecimal shareAmount;

   @ApiModelProperty(name = "share_level_method", value = "U团长管理佣金的方式0:按比例 1:按金额")
   private Boolean shareLevelMethod;

   @ApiModelProperty(name = "share_level_rate", value = "U团长管理佣金按比例")
   private BigDecimal shareLevelRate;

   @ApiModelProperty(name = "share_level_amount", value = "U团长管理佣金按金额")
   private BigDecimal shareLevelAmount;

   @ApiModelProperty(name = "share_second_method", value = "默认U达人参与分佣的方式0:按比例 1:按金额 ")
   private Boolean shareSecondMethod;

   @ApiModelProperty(name = "share_second_level_method", value = "默认U达人参与管理佣金的方式0:按比例 1:按金额 ")
   private Boolean shareSecondLevelMethod;

   @ApiModelProperty(name = "share_second_rate", value = "默认U达人分销佣金按比例")
   private BigDecimal shareSecondRate;

   @ApiModelProperty(name = "share_second_amount", value = "默认U达人分销佣金按金额")
   private BigDecimal shareSecondAmount;

   @ApiModelProperty(name = "share_second_level_rate", value = "默认U达人管理佣金按比例")
   private BigDecimal shareSecondLevelRate;

   @ApiModelProperty(name = "share_second_level_amount", value = "默认U达人管理佣金按金额")
   private BigDecimal shareSecondLevelAmount;

   @ApiModelProperty(name = "share_third_method", value = "默认U掌柜参与分佣的方式0:按比例 1:按金额 ")
   private Boolean shareThirdMethod;

   @ApiModelProperty(name = "share_third_level_method", value = "默认U掌柜参与管理佣金的方式0:按比例 1:按金额 ")
   private Boolean shareThirdLevelMethod;

   @ApiModelProperty(name = "share_third_rate", value = "默认U掌柜分销佣金按比例")
   private BigDecimal shareThirdRate;

   @ApiModelProperty(name = "share_third_amount", value = "默认U掌柜分销佣金按金额")
   private BigDecimal shareThirdAmount;

   @ApiModelProperty(name = "share_third_level_rate", value = "默认U掌柜管理佣金按比例")
   private BigDecimal shareThirdLevelRate;

   @ApiModelProperty(name = "share_third_level_amount", value = "默认U掌柜管理佣金按金额")
   private BigDecimal shareThirdLevelAmount;

   @ApiModelProperty(name = "upgrade_second_level_method", value = "U团长升级方式 0未开启 1交易额达到 2推荐合伙人数达到 3同时达到")
   private Integer upgradeSecondLevelMethod;

   @ApiModelProperty(name = "upgrade_second_level_max_trade", value = "U团长升级U达人 交易额")
   private Integer upgradeSecondLevelMaxTrade;

   @ApiModelProperty(name = "upgrade_second_level_max_partner", value = "U团长升级U达人 推荐U团长数")
   private Integer upgradeSecondLevelMaxPartner;

   @ApiModelProperty(name = "upgrade_third_level_method", value = "U掌柜升级方式 0未开启 1交易额达到 2推荐合伙人数达到 3同时达到")
   private Integer upgradeThirdLevelMethod;

   @ApiModelProperty(name = "upgrade_third_level_max_trade", value = "U掌柜升级U达人 交易额")
   private Integer upgradeThirdLevelMaxTrade;

   @ApiModelProperty(name = "upgrade_third_level_max_partner", value = "U掌柜升级U达人 推荐U团长数")
   private Integer upgradeThirdLevelMaxPartner;


}

