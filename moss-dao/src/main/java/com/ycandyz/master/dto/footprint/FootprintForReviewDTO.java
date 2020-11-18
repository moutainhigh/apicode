package com.ycandyz.master.dto.footprint;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * @Description 商友圈表 检索参数类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-27
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="商友圈表-检索参数")
public class FootprintForReviewDTO implements Serializable {

    @ApiModelProperty(value = "内容")
    private String content;

}
