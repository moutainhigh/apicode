package com.ycandyz.master.domain.query.taboo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * @Description 基础商品表 检索参数类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-23
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="敏感词全部展示入参")
public class BaseTabooWordsQuery implements Serializable {


    @ApiModelProperty(value = "词组名称")
    private String phraseName;

    @ApiModelProperty(value = "敏感词")
    private String tabooWord;

    @ApiModelProperty(value = "创建时间止开始")
    private Long createdTimeStart;

    @ApiModelProperty(value = "创建时间截止")
    private Long createdTimeEnd;

}
