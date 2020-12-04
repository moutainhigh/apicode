package com.ycandyz.master.api;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author SanGang
 */
@Data
@EqualsAndHashCode(callSuper = false)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PageModel<T> {

    @ApiModelProperty(name="page",value = "页码")
    private long page = 1;
    @ApiModelProperty(name="page_size",value = "页长")
    private long pageSize = 10;

}
