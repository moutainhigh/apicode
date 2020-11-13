package com.ycandyz.master.domain.model.mall;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ycandyz.master.validation.ValidatorContract;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * @Description 商品视频信息 检索参数类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-10
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="商品视频信息-参数")
public class MallItemVideoModel implements Serializable {

    @ApiModelProperty(value = "视频栏目(0置顶视频,1描述视频)")
    @NotNull(message = "视频栏目不能为空",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    @Range(min = 0, max = 1, message = "视频栏目类型不正确",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    private Integer type;

    @ApiModelProperty(value = "备注(审核不通过原因)")
    @ApiParam(hidden = true)
    private String remark;

}
