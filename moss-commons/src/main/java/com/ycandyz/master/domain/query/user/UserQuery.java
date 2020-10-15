package com.ycandyz.master.domain.query.user;

import com.ycandyz.master.annotation.Condition;
import com.ycandyz.master.enums.ConditionEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * @Description 用户表 检索参数类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-13
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="用户表-检索参数")
public class UserQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "后台用户id")
    @Condition(field = "id", condition = ConditionEnum.EQ)
    private Long id;

    @ApiModelProperty(value = "注册平台 0-企业 1-个人版app 2-代理商 3-有传后台 4-企业代理商合并 5-企业版app")
    private Integer registPlatfrom;

    @ApiModelProperty(value = "注册设备 0 ios/1 android/2 小程序/3 h5/4 有传后台/5 企业后台/6 代理商后台/7 其他")
    private Integer registDevice;

    @ApiModelProperty(value = "拼音串")
    private String namePinyin;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "性别 0-未知 1-男 2-女")
    private Boolean sex;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "头像url")
    private String headimg;

    @ApiModelProperty(value = "用户自己的邀请码")
    private String inviteCode;

    @ApiModelProperty(value = "邀请人id")
    private Long inviteUserId;

    @ApiModelProperty(value = "0没邀约/1企业管理员邀约/2加入/3拒绝")
    private Integer inviteStatus;

    @ApiModelProperty(value = "密码哈希")
    private String passwordHash;

    @ApiModelProperty(value = "苹果账号id")
    private String appstoreId;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "微信号")
    private String wxId;

    @ApiModelProperty(value = "微信小程序openId")
    private String wxMiniOpenId;

    @ApiModelProperty(value = "微信公众号openId")
    private String wxOpenId;

    @ApiModelProperty(value = "微信unionId")
    private String wxUnionId;

    @ApiModelProperty(value = "微信公众号openid")
    private String wxGzhOpenId;

    @ApiModelProperty(value = "是否实名认证 0未认证/1已认证")
    private Boolean isAuth;

    @ApiModelProperty(value = "是否禁用 0-启用 1-禁用")
    private Boolean isDisable;

    @ApiModelProperty(value = "禁用原因")
    private String disableReason;

    @ApiModelProperty(value = "是否删除 0-正常 1-已删除")
    private Boolean isDel;

    @ApiModelProperty(value = "创建时间")
    private Integer createdAt;

    @ApiModelProperty(value = "更新时间")
    private Integer updatedAt;

    @ApiModelProperty(value = "区块链id")
    private String blockChainId;

    @ApiModelProperty(value = "区块链私钥")
    private String blockChainPrivateKey;

    @ApiModelProperty(value = "区块链公钥")
    private String blockChainPublicKey;



}
