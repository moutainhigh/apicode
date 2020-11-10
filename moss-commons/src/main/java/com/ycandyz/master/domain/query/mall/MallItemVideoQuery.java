package com.ycandyz.master.domain.query.mall;

import com.ycandyz.master.annotation.Condition;
import com.ycandyz.master.enums.ConditionEnum;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@ApiModel(description="商品视频信息-检索参数")
public class MallItemVideoQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品编号")
    private String itemNo;

    @ApiModelProperty(value = "视频类型")
    private Integer type;

    @ApiModelProperty(value = "视频标题")
    private String title;

    @ApiModelProperty(value = "状态(审核用)")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

}
