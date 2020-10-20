package com.ycandyz.master.controller.base;

import com.ycandyz.master.annotation.Condition;
import com.ycandyz.master.enums.ConditionEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * @Description 基参
 * </p>
 *
 * @author sangang
 * @since 2019-07-14
 * @version 2.0
 */
@Data
public abstract class BaseQuery implements Serializable {

    @Condition(condition = ConditionEnum.EQ)
    @ApiModelProperty(value = "唯一主键ID")
    public Long id;
	
    @Condition(condition = ConditionEnum.GE, field = "create_date")
	@ApiModelProperty(value = "创建时间起")
	public Date createDateS;
	  
	@Condition(condition = ConditionEnum.LE, field = "create_date")
	@ApiModelProperty(value = "创建时间止")
	public Date createDateE;
	  
	@Condition(condition = ConditionEnum.GE, field = "update_date")
	@ApiModelProperty(value = "修改时间起")
	public Date updateDateS;
	  
	@Condition(condition = ConditionEnum.LE, field = "update_date")
	@ApiModelProperty(value = "修改时间止")
	public Date updateDateE;
	 

}
