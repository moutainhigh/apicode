package com.ycandyz.master.domain.query.organize;

import com.ycandyz.master.annotation.Condition;
import com.ycandyz.master.enums.ConditionEnum;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * @Description 集团表 检索参数类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-23
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="集团表-检索参数")
public class OrganizeGroupQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @Condition(field = "id", condition = ConditionEnum.EQ)
    private Integer id;

    private Integer organizeId;

    @ApiModelProperty(value = "集团名字")
    private String groupName;

    @ApiModelProperty(value = "创建日期起")
    @Condition(field = "created_at", condition = ConditionEnum.GE)
    private Date createdAtS;
    
    @ApiModelProperty(value = "创建日期止")
    @Condition(field = "created_at", condition = ConditionEnum.LE)
    private Date createdAtE;

    @ApiModelProperty(value = "创建人id")
    private Integer creater;

    @ApiModelProperty(value = "锁客状态 0 关闭 1开启")
    private Boolean lockStatus;



}
