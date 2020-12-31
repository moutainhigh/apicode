package com.ycandyz.master.domain;

import com.ycandyz.master.entities.user.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ApiModel(value="订单详情", description="订单详情查询VO")
public class UserVO extends User {

//    @ApiModelProperty(value = "后台用户id")
//    private Long id;

    @ApiModelProperty(value = "注册平台 0-企业 1-个人版app 2-代理商 3-有传后台 4-企业代理商合并 5-企业版app")
    private Integer registPlatfrom;

    @ApiModelProperty(value = "注册设备 0 ios/1 android/2 小程序/3 h5/4 有传后台/5 企业后台/6 代理商后台/7 其他")
    private Integer registDevice;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "性别 0-未知 1-男 2-女")
    private Integer sex;

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

    @ApiModelProperty(value = "0-企业 1-前端 2-代理商 3-有传 4-U客后台 5-U客App")
    private Integer platform;

    @ApiModelProperty(value = "企业ID")
    private Long organizeId;

    @ApiModelProperty(value = "前段按钮ID")
    private Long menuId;

    @ApiModelProperty(value = "商店号")
    private String shopNo;

    @ApiModelProperty(value = "来源 App MiniProgram web")
    private String source;
}
