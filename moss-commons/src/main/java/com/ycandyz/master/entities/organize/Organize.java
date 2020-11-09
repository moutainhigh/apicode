package com.ycandyz.master.entities.organize;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.math.BigDecimal;
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
 * @Description 组织表 数据表字段映射类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-09
 * @version 2.0
 */
@Getter
@Setter
@TableName("organize")
@ApiModel(description="组织表")
public class Organize extends Model {


   @ApiModelProperty(value = "组织id")
   @TableId(value = "id", type = IdType.AUTO)
   private Integer id;

   @ApiModelProperty(value = "组织类型 1-企业 2-组织 3-政府")
   private Boolean type;

   @ApiModelProperty(value = "组织短名称")
   private String shortName;

   @ApiModelProperty(value = "组织全称")
   private String fullName;

   @ApiModelProperty(value = "省份")
   private String province;

   @ApiModelProperty(value = "省id")
   private Integer provinceId;

   @ApiModelProperty(value = "市")
   private String city;

   @ApiModelProperty(value = "市id")
   private Integer cityId;

   @ApiModelProperty(value = "区")
   private String area;

   @ApiModelProperty(value = "区id")
   private Integer areaId;

   @ApiModelProperty(value = "地址")
   private String address;

   @ApiModelProperty(value = "区域编码")
   private Integer regionCode;

   @ApiModelProperty(value = "一级分类id")
   private Integer firstClassifyId;

   @ApiModelProperty(value = "行业一级分类")
   private String firstClassify;

   @ApiModelProperty(value = "二级分类id")
   private Integer secondClassifyId;

   @ApiModelProperty(value = "行业二级分类")
   private String secondClassify;

   @ApiModelProperty(value = "经度")
   private String longitude;

   @ApiModelProperty(value = "纬度")
   private String latitude;

   @ApiModelProperty(value = "企业基本联系人")
   private String baseContactName;

   @ApiModelProperty(value = "联系电话")
   private String phone;

   @ApiModelProperty(value = "邮箱")
   private String email;

   @ApiModelProperty(value = "企业logo")
   private String logo;

   @ApiModelProperty(value = "企业介绍")
   private String profile;

   @ApiModelProperty(value = "企业官网二维码")
   private String officialQrcode;

   @ApiModelProperty(value = "企业规模 1：少于15人  2：15-50 人 3：50-150人  4：150-500人 5：500-2000人 6：2000人以上")
   private Boolean scale;

   @ApiModelProperty(value = "营业执照图片地址")
   private String businessLicense;

   @ApiModelProperty(value = "营业注册号")
   private String businessLicenseCode;

   @ApiModelProperty(value = "营业执照开始日期")
   private Long businessLicenseStart;

   @ApiModelProperty(value = "营业执照结束日期")
   private Long businessLicenseEnd;

   @ApiModelProperty(value = "企业联系人")
   private String contactName;

   @ApiModelProperty(value = "企业联系人电话")
   private String contactPhone;

   @ApiModelProperty(value = "授权书地址")
   private String authFile;

   @ApiModelProperty(value = "名片购买总数")
   private Integer cardNum;

   @ApiModelProperty(value = "名片剩余数")
   private Integer cardRestNum;

   @ApiModelProperty(value = "服务开始时间")
   private Long serviceStart;

   @ApiModelProperty(value = "服务结束时间")
   private Long serviceEnd;

   @ApiModelProperty(value = "消费金额")
   private BigDecimal payedMoney;

   @ApiModelProperty(value = "企业云盘大小")
   private Integer cloudSize;

   @ApiModelProperty(value = "已使用云盘大小")
   private Integer cloudUsedSize;

   @ApiModelProperty(value = "人工审核开关 ：0-不开启 1-已开启")
   private Boolean openAuth;

   @ApiModelProperty(value = "验证方式： 1-无需图片验证 2-员工照片验证 3-员工身份证验证 4-员工工牌验证 ")
   private Boolean authType;

   @ApiModelProperty(value = "认证备注")
   private String authRemark;

   @ApiModelProperty(value = "是否认证 1-未认证 2-认证中 3-已认证  4- 已失效")
   private Boolean status;

   @ApiModelProperty(value = "是否禁用 0-启用 1-禁用")
   private Boolean isDisable;

   @ApiModelProperty(value = "禁用备注")
   private String disableRemark;

   @ApiModelProperty(value = "禁用原因")
   private String disableReason;

   @ApiModelProperty(value = "是否删除 0-正常 1-已删除")
   private Boolean isDel;

   @ApiModelProperty(value = "是否加入企业广场  0-不加入 1-加入")
   private Boolean isAddSquare;

   @ApiModelProperty(value = "禁用时间")
   private Integer disabledAt;

   @ApiModelProperty(value = "删除时间")
   private Integer deletedAt;

   @ApiModelProperty(value = "邀请码")
   private String inviteCode;

   @ApiModelProperty(value = "父级id  由代理商邀请注册的用户所创建的企业都是该代理商的代理企业，成代理商的id为本企业的fid")
   private Integer fatherId;

   @ApiModelProperty(value = "邀请的企业员工id")
   private Integer inviteEmployeeId;

   @ApiModelProperty(value = "邀请用户的id")
   private Integer inviteUserId;

   @ApiModelProperty(value = "是否为代理企业  0：否  1：是")
   private Boolean isAgent;

   @ApiModelProperty(value = "代理商审核状态  0-待审核 1-审核通过 2-审核未通过 3-无审核状态")
   private Boolean agentAuditStatus;

   @ApiModelProperty(value = "代理商审核时间")
   private Integer agentAuditAt;

   @ApiModelProperty(value = "审核备注。在审核不通过时使用")
   private String agentAuditRemak;

   @ApiModelProperty(value = "代理商状态  0-未开通 1-正常 2-已禁用  3-已注销")
   private Boolean agentStatus;

   @ApiModelProperty(value = "申请代理时间")
   private Integer agentApplyAt;

   @ApiModelProperty(value = "代理商禁用原因")
   private String agentDisableRemark;

   @ApiModelProperty(value = "代理商禁用时间")
   private Integer agentDisableAt;

   @ApiModelProperty(value = "代理开始时间")
   private Long agentBeg;

   @ApiModelProperty(value = "代理结束时间")
   private Long agentEnd;

   @ApiModelProperty(value = "创建时间")
   private Integer createdAt;

   @ApiModelProperty(value = "更新时间")
   private Integer updatedAt;

   @ApiModelProperty(value = "创建者id，即该企业所属用户id")
   private Integer userId;

   @ApiModelProperty(value = "企业状态  0-初始状态  1-试用状态  2-服务状态")
   private Boolean state;

   @ApiModelProperty(value = "企业服务状态 1-仅注册 2-试用中 3-试用到期 4-服务中 5-服务到期")
   private Boolean serviceStatus;

   @ApiModelProperty(value = "企业临时认证状态  1-未认证 2-认证中 3-已认证  4- 已失效  （根据status,business_license_end,和最新认证记录来做的一个综合判断）")
   private Boolean tempStatus;

   @ApiModelProperty(value = "是否显示标记为公开成员的名片")
   private Boolean isPublicCardShow;

   @ApiModelProperty(value = "是否显示加入红人榜的名片（不需要标记是否为公开成员）是否显示加入红人榜的名片（不需要标记是否为公开成员），只有is_public_card_show为1时，才有效")
   private Boolean isPublicCardRedShow;

   @ApiModelProperty(value = "余额")
   private BigDecimal balance;

   @ApiModelProperty(value = "首字母")
   private String alphabet;

   @ApiModelProperty(value = "是否开通商城  0-否  1-是")
   private Boolean openMall;

   @ApiModelProperty(value = "是否开通短信服务  0-否  1-是")
   private Boolean openSms;

   @ApiModelProperty(value = "是否开通收银台  0-否  1-是")
   private Boolean openCashier;

   @ApiModelProperty(value = "累计购买的服务天数")
   private Integer serviceDays;

   @ApiModelProperty(value = "是否开启区块链  0-否  1-是")
   private Boolean openBlockChain;

   @ApiModelProperty(value = "区块链id")
   private String blockChainId;

   @ApiModelProperty(value = "区块链私钥")
   private String blockChainPrivateKey;

   @ApiModelProperty(value = "区块链公钥")
   private String blockChainPublicKey;

   @ApiModelProperty(value = "区块链标识")
   private String blockChainRecordId;

   @ApiModelProperty(value = "是否是集团.  1是集团 0不是")
   private Integer isGroup;

   @ApiModelProperty(value = "通联设置账号")
   private String tlBizUserId;


}
