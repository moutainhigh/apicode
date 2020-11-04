package com.ycandyz.master.model.taboo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 基础商品表 VO
 * </p>
 *
 * @author SanGang
 * @since 2020-10-23
 * @version 2.0
 */
@Getter
@Setter
public class BaseTabooWordsVO {

    private Long id;

    @ApiModelProperty(value = "词组名称")
    private String phraseName;

    @ApiModelProperty(value = "敏感词")
    private String[] tabooWords;

    @ApiModelProperty(value = "处理方式")
    private Integer treatmentMethod;

}
