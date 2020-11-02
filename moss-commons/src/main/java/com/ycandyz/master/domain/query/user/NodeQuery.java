package com.ycandyz.master.domain.query.user;

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
 * @Description 权限节点表 检索参数类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-01
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="权限节点表-检索参数")
public class NodeQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权限节点id")
    @Condition(field = "id", condition = ConditionEnum.EQ)
    private Long id;

    @ApiModelProperty(value = "服务端类型(0:Python,1:java,2:Go)")
    private Integer type;

    @ApiModelProperty(value = "0 get/1 post/2 put/3 delete")
    private String methodType;

    @ApiModelProperty(value = "路由地址")
    private String uri;

    @ApiModelProperty(value = "事件")
    private String action;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "0启用/1停用")
    private Integer status;

    @ApiModelProperty(value = "更新时间")
    private Integer updatedAt;

    @ApiModelProperty(value = "创建时间")
    private Integer createdAt;



}
