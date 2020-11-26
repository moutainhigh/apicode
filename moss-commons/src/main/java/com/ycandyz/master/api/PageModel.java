package com.ycandyz.master.api;

import com.fasterxml.jackson.annotation.JsonAlias;
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
    @JsonAlias({"page","pageNum"})
    private long pageNum = 1;

    @ApiModelProperty(value = "页长")
    @JsonAlias({"page_size","pageSize"})
    private long pageSize = 10;

}
