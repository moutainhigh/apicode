package com.ycandyz.master.domain.model.mall;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ycandyz.master.annotation.Condition;
import com.ycandyz.master.enums.ConditionEnum;
import com.ycandyz.master.validation.ValidatorContract;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * @Description 商品视频投诉 检索参数类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-10
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="商品视频投诉-参数")
public class MallItemVideoComplaintModel implements Serializable {

    @Size(max = 64, message = "视频编号长度不能大于64。",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    @NotNull(message = "视频编号不能为空",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    @ApiModelProperty(value = "商品视频编号")
    private String videoNo;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "投诉类型()")
    private Integer type;

    @Size(max = 1000, message = "视频投诉内容长度不能大于1000。",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    @ApiModelProperty(value = "视频投诉内容")
    private String content;


}
