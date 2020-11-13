package com.ycandyz.master.domain.query.msg;

import com.ycandyz.master.annotation.Condition;
import com.ycandyz.master.enums.ConditionEnum;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * @Description 业务通知权限表 检索参数类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-13
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="业务通知权限表-检索参数")
public class MsgNotifyRoleQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "业务通知权限表主键")
    @Condition(field = "id", condition = ConditionEnum.EQ)
    private Integer id;

    @ApiModelProperty(value = "查看者的权限类型")
    private Integer roleType;

    @ApiModelProperty(value = "查看者权限类型对应的在系统中的角色id")
    private Integer roleId;

    @ApiModelProperty(value = "状态：0正常，1删除")
    private Integer isDel;

    @ApiModelProperty(value = "更新时间")
    private Long updatedAt;

    @ApiModelProperty(value = "创建时间")
    private Long createdAt;



}
