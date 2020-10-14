package com.ycandyz.master.base.extension;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * @Description 逻辑删除标识
 * </p>
 *
 * @author sangang
 * @since 2019-07-14
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class BaseDelDO extends Model {

	@ApiParam(hidden = true)
    @TableLogic(value = "0", delval = "1")
    //@TableField(value = "deleted", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "逻辑删除标志")
    private Integer deleted;

}
