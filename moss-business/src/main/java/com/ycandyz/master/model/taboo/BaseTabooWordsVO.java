package com.ycandyz.master.model.taboo;

import com.ycandyz.master.validation.ValidatorContract;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

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

    @ApiModelProperty(value = "词组名称" ,required = true)
    @NotNull(message = "词组名称不能为空",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    private String phraseName;

    @ApiModelProperty(value = "敏感词组" ,required = true)
    @NotNull(message = "敏感词组不能为空",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    private String[] tabooWords;

    @ApiModelProperty(value = "处理方式 [0:发布屏蔽]" ,required = true)
    @NotNull(message = "处理方式不能为空",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    private Integer treatmentMethod;

}
