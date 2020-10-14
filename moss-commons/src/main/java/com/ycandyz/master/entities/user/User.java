package com.ycandyz.master.entities.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 用户信息表
 * @Author: Wang Yang
 * @Date:   2020-09-23
 * @Version: V1.0
 */
@Data
@TableName("user")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="user对象", description="用户信息表")
public class User {
    
	/**后台用户id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "后台用户id")
	private Integer id;
	/**注册平台 0-企业 1-个人版app 2-代理商 3-有传后台 4-企业代理商合并 5-企业版app*/
    @ApiModelProperty(value = "注册平台 0-企业 1-个人版app 2-代理商 3-有传后台 4-企业代理商合并 5-企业版app")
	private Integer registPlatfrom;
	/**注册设备 0 ios/1 android/2 小程序/3 h5/4 有传后台/5 企业后台/6 代理商后台/7 其他*/
    @ApiModelProperty(value = "注册设备 0 ios/1 android/2 小程序/3 h5/4 有传后台/5 企业后台/6 代理商后台/7 其他")
	private Integer registDevice;
	/**拼音串*/
    @ApiModelProperty(value = "拼音串")
	private String namePinyin;
	/**姓名*/
    @ApiModelProperty(value = "姓名")
	private String name;
	/**昵称*/
    @ApiModelProperty(value = "昵称")
	private String nickname;
	/**性别 0-未知 1-男 2-女*/
    @ApiModelProperty(value = "性别 0-未知 1-男 2-女")
	private Integer sex;
	/**手机号码*/
    @ApiModelProperty(value = "手机号码")
	private String phone;
	/**头像url*/
    @ApiModelProperty(value = "头像url")
	private String headimg;
	/**用户自己的邀请码*/
    @ApiModelProperty(value = "用户自己的邀请码")
	private String inviteCode;
	/**邀请人id 邀请人id*/
    @ApiModelProperty(value = "邀请人id 邀请人id")
	private Integer inviteUserId;
	/**0没邀约/1企业管理员邀约/2加入/3拒绝*/
    @ApiModelProperty(value = "0没邀约/1企业管理员邀约/2加入/3拒绝")
	private Integer inviteStatus;
	/**密码哈希*/
    @ApiModelProperty(value = "密码哈希")
	private String passwordHash;
	/**苹果账号id*/
    @ApiModelProperty(value = "苹果账号id")
	private String appstoreId;
	/**邮箱*/
    @ApiModelProperty(value = "邮箱")
	private String email;
	/**微信号*/
    @ApiModelProperty(value = "微信号")
	private String wxId;
	/**微信小程序openId*/
    @ApiModelProperty(value = "微信小程序openId")
	private String wxMiniOpenId;
	/**微信公众号openId*/
    @ApiModelProperty(value = "微信公众号openId")
	private String wxOpenId;
	/**微信unionId*/
    @ApiModelProperty(value = "微信unionId")
	private String wxUnionId;
	/**是否实名认证 0未认证/1已认证*/
    @ApiModelProperty(value = "是否实名认证 0未认证/1已认证")
	private Integer isAuth;
	/**是否禁用 0-启用 1-禁用*/
    @ApiModelProperty(value = "是否禁用 0-启用 1-禁用")
	private Integer isDisable;
	/**禁用原因*/
    @ApiModelProperty(value = "禁用原因")
	private String disableReason;
	/**是否删除 0-正常 1-已删除*/
    @ApiModelProperty(value = "是否删除 0-正常 1-已删除")
	private Integer isDel;
	/**创建时间*/
    @ApiModelProperty(value = "创建时间")
	private Integer createdAt;
	/**更新时间*/
    @ApiModelProperty(value = "更新时间")
	private Integer updatedAt;
	/**区块链id*/
    @ApiModelProperty(value = "区块链id")
	private String blockChainId;
	/**区块链私钥*/
    @ApiModelProperty(value = "区块链私钥")
	private String blockChainPrivateKey;
	/**区块链公钥*/
    @ApiModelProperty(value = "区块链公钥")
	private String blockChainPublicKey;
	/**微信公众号openid*/
    @ApiModelProperty(value = "微信公众号openid")
	private String wxGzhOpenId;
}
