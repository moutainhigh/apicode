package com.ycandyz.master.domain.query.mall;

import com.ycandyz.master.annotation.Condition;
import com.ycandyz.master.enums.ConditionEnum;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@ApiModel(description="商品视频投诉-检索参数")
public class MallItemVideoComplaintQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品视频编号")
    private String videoNo;

    @ApiModelProperty(value = "投诉类型(预留)")
    private Integer type;

    @ApiModelProperty(value = "视频投诉内容")
    private String content;

    @ApiModelProperty(value = "投诉状态(0未处理,1已处理)")
    private Integer status;

}