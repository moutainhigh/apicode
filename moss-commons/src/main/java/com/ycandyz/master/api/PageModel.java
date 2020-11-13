package com.ycandyz.master.api;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author SanGang
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PageModel<T> {

    @ApiModelProperty(value = "页码")
    private long pageNum = 1;
    @ApiModelProperty(value = "页长")
    private long pageSize = 10;

}
