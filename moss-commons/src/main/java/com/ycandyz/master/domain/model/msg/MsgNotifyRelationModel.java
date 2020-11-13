package com.ycandyz.master.domain.model.msg;

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
 * @Description 业务通知关系表 检索参数类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-13
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="业务通知关系-参数")
public class MsgNotifyRelationModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "业务通知关系表主键")
    @Condition(field = "id", condition = ConditionEnum.EQ)
    private Integer id;

    @ApiModelProperty(value = "业务通知表主键")
    private Integer msgNotifyId;

    @ApiModelProperty(value = "通知类型：1系统通知，2服务通知")
    private Integer notifyType;

    @ApiModelProperty(value = "查看者的权限类型")
    private Integer roleType;

    @ApiModelProperty(value = "关系类型：1 企业通知 2 代理通知 3有传平台")
    private Integer relationType;

    @ApiModelProperty(value = "关系ID： 对应类型的主键ID，企业类型为organize_id，代理类型为agent_id，有传类型为0")
    private Long relationId;

    @ApiModelProperty(value = "状态：0正常，1删除")
    private Integer isDel;

    @ApiModelProperty(value = "是否已读：0否，1是")
    private Integer isRead;

    @ApiModelProperty(value = "更新时间")
    private Long updatedAt;

    @ApiModelProperty(value = "创建时间")
    private Long createdAt;



}
