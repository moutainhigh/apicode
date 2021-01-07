package com.ycandyz.master.domain.model.organize;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
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
 * @Description 组织表 Model
 * </p>
 *
 * @author SanGang
 * @since 2021-01-05
 * @version 2.0
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description="组织表-Model")
public class OrganizeModel implements Serializable {

   private static final long serialVersionUID = 1L;

   @ApiModelProperty(name = "id", value = "组织id")
   private Integer id;

   @ApiModelProperty(name = "type", value = "组织类型 1-企业 2-组织 3-政府")
   private Boolean type;

   @ApiModelProperty(name = "short_name", value = "组织短名称")
   private String shortName;

   @ApiModelProperty(name = "full_name", value = "组织全称")
   private String fullName;

   @ApiModelProperty(name = "province", value = "省份")
   private String province;

   @ApiModelProperty(name = "province_id", value = "省id")
   private Integer provinceId;

   @ApiModelProperty(name = "city", value = "市")
   private String city;

   @ApiModelProperty(name = "city_id", value = "市id")
   private Integer cityId;

   @ApiModelProperty(name = "area", value = "区")
   private String area;

   @ApiModelProperty(name = "area_id", value = "区id")
   private Integer areaId;

   @ApiModelProperty(name = "address", value = "地址")
   private String address;

   @ApiModelProperty(name = "region_code", value = "区域编码")
   private Integer regionCode;

   @ApiModelProperty(name = "first_classify_id", value = "一级分类id")
   private Integer firstClassifyId;

   @ApiModelProperty(name = "first_classify", value = "行业一级分类")
   private String firstClassify;

   @ApiModelProperty(name = "second_classify_id", value = "二级分类id")
   private Integer secondClassifyId;

   @ApiModelProperty(name = "second_classify", value = "行业二级分类")
   private String secondClassify;

   @ApiModelProperty(name = "longitude", value = "经度")
   private String longitude;

   @ApiModelProperty(name = "latitude", value = "纬度")
   private String latitude;

   @ApiModelProperty(name = "base_contact_name", value = "企业基本联系人")
   private String baseContactName;

   @ApiModelProperty(name = "phone", value = "联系电话")
   private String phone;

   @ApiModelProperty(name = "email", value = "邮箱")
   private String email;

   @ApiModelProperty(name = "logo", value = "企业logo")
   private String logo;

   @ApiModelProperty(name = "profile", value = "企业介绍")
   private String profile;

   @ApiModelProperty(name = "official_qrcode", value = "企业官网二维码")
   private String officialQrcode;

   @ApiModelProperty(name = "scale", value = "企业规模 1：少于15人  2：15-50 人 3：50-150人  4：150-500人 5：500-2000人 6：2000人以上")
   private Boolean scale;

   @ApiModelProperty(name = "business_license", value = "营业执照图片地址")
   private String businessLicense;

   @ApiModelProperty(name = "business_license_code", value = "营业注册号")
   private String businessLicenseCode;

   @ApiModelProperty(name = "business_license_start", value = "营业执照开始日期")
   private Long businessLicenseStart;

   @ApiModelProperty(name = "business_license_end", value = "营业执照结束日期")
   private Long businessLicenseEnd;

   @ApiModelProperty(name = "contact_name", value = "企业联系人")
   private String contactName;

   @ApiModelProperty(name = "contact_phone", value = "企业联系人电话")
   private String contactPhone;

   @ApiModelProperty(name = "auth_file", value = "授权书地址")
   private String authFile;

   @ApiModelProperty(name = "card_num", value = "名片购买总数")
   private Integer cardNum;

   @ApiModelProperty(name = "card_rest_num", value = "名片剩余数")
   private Integer cardRestNum;

   @ApiModelProperty(name = "service_start", value = "服务开始时间")
   private Long serviceStart;

   @ApiModelProperty(name = "service_end", value = "服务结束时间")
   private Long serviceEnd;

   @ApiModelProperty(name = "payed_money", value = "消费金额")
   private BigDecimal payedMoney;

   @ApiModelProperty(name = "cloud_size", value = "企业云盘大小")
   private Integer cloudSize;

   @ApiModelProperty(name = "cloud_used_size", value = "已使用云盘大小")
   private Integer cloudUsedSize;

   @ApiModelProperty(name = "open_auth", value = "人工审核开关 ：0-不开启 1-已开启")
   private Boolean openAuth;

   @ApiModelProperty(name = "auth_type", value = "验证方式： 1-无需图片验证 2-员工照片验证 3-员工身份证验证 4-员工工牌验证 ")
   private Boolean authType;

   @ApiModelProperty(name = "auth_remark", value = "认证备注")
   private String authRemark;

   @ApiModelProperty(name = "status", value = "是否认证 1-未认证 2-认证中 3-已认证  4- 已失效")
   private Boolean status;

   @ApiModelProperty(name = "is_disable", value = "是否禁用 0-启用 1-禁用")
   private Boolean isDisable;

   @ApiModelProperty(name = "disable_remark", value = "禁用备注")
   private String disableRemark;

   @ApiModelProperty(name = "disable_reason", value = "禁用原因")
   private String disableReason;

   @ApiModelProperty(name = "is_del", value = "是否删除 0-正常 1-已删除")
   private Boolean isDel;

   @ApiModelProperty(name = "is_add_square", value = "是否加入企业广场  0-不加入 1-加入")
   private Boolean isAddSquare;

   @ApiModelProperty(name = "disabled_at", value = "禁用时间")
   private Integer disabledAt;

   @ApiModelProperty(name = "deleted_at", value = "删除时间")
   private Integer deletedAt;

   @ApiModelProperty(name = "invite_code", value = "邀请码")
   private String inviteCode;

   @ApiModelProperty(name = "father_id", value = "父级id  由代理商邀请注册的用户所创建的企业都是该代理商的代理企业，成代理商的id为本企业的fid")
   private Integer fatherId;

   @ApiModelProperty(name = "invite_employee_id", value = "邀请的企业员工id")
   private Integer inviteEmployeeId;

   @ApiModelProperty(name = "invite_user_id", value = "邀请用户的id")
   private Integer inviteUserId;

   @ApiModelProperty(name = "invite_batch_number", value = "邀请前置码的批次号")
   private String inviteBatchNumber;

   @ApiModelProperty(name = "is_agent", value = "是否为代理企业  0：否  1：是")
   private Boolean isAgent;

   @ApiModelProperty(name = "agent_audit_status", value = "代理商审核状态  0-待审核 1-审核通过 2-审核未通过 3-无审核状态")
   private Boolean agentAuditStatus;

   @ApiModelProperty(name = "agent_audit_at", value = "代理商审核时间")
   private Integer agentAuditAt;

   @ApiModelProperty(name = "agent_audit_remak", value = "审核备注。在审核不通过时使用")
   private String agentAuditRemak;

   @ApiModelProperty(name = "agent_status", value = "代理商状态  0-未开通 1-正常 2-已禁用  3-已注销")
   private Boolean agentStatus;

   @ApiModelProperty(name = "agent_apply_at", value = "申请代理时间")
   private Integer agentApplyAt;

   @ApiModelProperty(name = "agent_disable_remark", value = "代理商禁用原因")
   private String agentDisableRemark;

   @ApiModelProperty(name = "agent_disable_at", value = "代理商禁用时间")
   private Integer agentDisableAt;

   @ApiModelProperty(name = "agent_beg", value = "代理开始时间")
   private Long agentBeg;

   @ApiModelProperty(name = "agent_end", value = "代理结束时间")
   private Long agentEnd;

   @ApiModelProperty(name = "created_at", value = "创建时间")
   private Integer createdAt;

   @ApiModelProperty(name = "updated_at", value = "更新时间")
   private Integer updatedAt;

   @ApiModelProperty(name = "user_id", value = "创建者id，即该企业所属用户id")
   private Integer userId;

   @ApiModelProperty(name = "state", value = "企业状态  0-初始状态  1-试用状态  2-服务状态")
   private Boolean state;

   @ApiModelProperty(name = "service_status", value = "企业服务状态 1-仅注册 2-试用中 3-试用到期 4-服务中 5-服务到期")
   private Boolean serviceStatus;

   @ApiModelProperty(name = "temp_status", value = "企业临时认证状态  1-未认证 2-认证中 3-已认证  4- 已失效  （根据status,business_license_end,和最新认证记录来做的一个综合判断）")
   private Boolean tempStatus;

   @ApiModelProperty(name = "is_public_card_show", value = "是否显示标记为公开成员的名片")
   private Boolean isPublicCardShow;

   @ApiModelProperty(name = "is_public_card_red_show", value = "是否显示加入红人榜的名片（不需要标记是否为公开成员），只有is_public_card_show为1时，才有效")
   private Boolean isPublicCardRedShow;

   @ApiModelProperty(name = "balance", value = "余额")
   private BigDecimal balance;

   @ApiModelProperty(name = "open_mall", value = "是否开通商城  0-否  1-是")
   private Boolean openMall;

   @ApiModelProperty(name = "alphabet", value = "首字母")
   private String alphabet;

   @ApiModelProperty(name = "open_sms", value = "是否开通短信服务  0-否  1-是")
   private Boolean openSms;

   @ApiModelProperty(name = "service_days", value = "累计购买的服务天数")
   private Integer serviceDays;

   @ApiModelProperty(name = "open_block_chain", value = "是否开启区块链  0-否  1-是")
   private Boolean openBlockChain;

   @ApiModelProperty(name = "block_chain_id", value = "区块链id")
   private String blockChainId;

   @ApiModelProperty(name = "block_chain_private_key", value = "区块链私钥")
   private String blockChainPrivateKey;

   @ApiModelProperty(name = "block_chain_public_key", value = "区块链公钥")
   private String blockChainPublicKey;

   @ApiModelProperty(name = "block_chain_record_id", value = "区块链标识")
   private String blockChainRecordId;

   @ApiModelProperty(name = "open_cashier", value = "是否开通收银台  0-否  1-是")
   private Boolean openCashier;

   @ApiModelProperty(name = "is_group", value = "是否是集团.  1是集团 0不是")
   private Integer isGroup;

   @ApiModelProperty(name = "tl_biz_user_id", value = "通联设置账号")
   private String tlBizUserId;

   @ApiModelProperty(name = "plan_id", value = "应用小程序方案模板编号")
   private Integer planId;


}

