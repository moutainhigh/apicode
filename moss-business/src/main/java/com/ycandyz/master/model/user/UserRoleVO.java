package com.ycandyz.master.model.user;

import com.ycandyz.master.annotation.Condition;
import com.ycandyz.master.enums.ConditionEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ApiModel(value="用户权限表", description="用户权限查询VO")
public class UserRoleVO {

    @ApiModelProperty(value = "后台用户角色id")
    @Condition(field = "id", condition = ConditionEnum.EQ)
    private Long id;
    @ApiModelProperty(value = "0-企业 1-前端 2-代理商 3-有传")
    private Integer platform;
    @ApiModelProperty(value = "企业id")
    private Long organizeId;
    @ApiModelProperty(value = "用户id")
    private Long userId;
    @ApiModelProperty(value = "账号类型 0员工/1合伙人")
    private Integer userType;
    @ApiModelProperty(value = "角色id")
    private Long roleId;
    @ApiModelProperty(value = "角色类型 0企业后台/ 1u客app")
    private Integer roleType;
    @ApiModelProperty(value = "app是否超管 0否/1是")
    private Integer is_boss;
    @ApiModelProperty(value = "0启用/1停用/2拒绝/3注销/4删除")
    private Integer status;
    @ApiModelProperty(value = "更新时间")
    private Integer updatedAt;
    @ApiModelProperty(value = "创建时间")
    private Integer created_at;
    @ApiModelProperty(value = "勾选部门集合")
    private String viewDepartmentIds;
    @ApiModelProperty(value = "邀请记录id")
    private Long inviteRecordId;
}
