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
 * @Description  检索参数类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-23
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="-检索参数")
public class OrganizeRelQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @Condition(field = "id", condition = ConditionEnum.EQ)
    private Integer id;

    @ApiModelProperty(value = "起")
    @Condition(field = "created_at", condition = ConditionEnum.GE)
    private Date createdAtS;
    
    @ApiModelProperty(value = "止")
    @Condition(field = "created_at", condition = ConditionEnum.LE)
    private Date createdAtE;

    @ApiModelProperty(value = "起")
    @Condition(field = "deleted_at", condition = ConditionEnum.GE)
    private Date deletedAtS;
    
    @ApiModelProperty(value = "止")
    @Condition(field = "deleted_at", condition = ConditionEnum.LE)
    private Date deletedAtE;

    private Integer userId;

    private Integer userId1;

    @ApiModelProperty(value = "集团企业id")
    private Integer groupOrganizeId;

    @ApiModelProperty(value = "子企业id")
    private Integer organizeId;

    @ApiModelProperty(value = "1待授权 2已授权.3已拒绝 4 已解除 5已取消")
    private Integer status;

    @ApiModelProperty(value = "授权日期 关联日期起")
    @Condition(field = "rel_at", condition = ConditionEnum.GE)
    private Date relAtS;
    
    @ApiModelProperty(value = "授权日期 关联日期止")
    @Condition(field = "rel_at", condition = ConditionEnum.LE)
    private Date relAtE;

    @ApiModelProperty(value = "解除授权日期起")
    @Condition(field = "unrel_at", condition = ConditionEnum.GE)
    private Date unrelAtS;
    
    @ApiModelProperty(value = "解除授权日期止")
    @Condition(field = "unrel_at", condition = ConditionEnum.LE)
    private Date unrelAtE;

    @ApiModelProperty(value = "起")
    @Condition(field = "updated_at", condition = ConditionEnum.GE)
    private Date updatedAtS;
    
    @ApiModelProperty(value = "止")
    @Condition(field = "updated_at", condition = ConditionEnum.LE)
    private Date updatedAtE;

    private Integer relType;



}
