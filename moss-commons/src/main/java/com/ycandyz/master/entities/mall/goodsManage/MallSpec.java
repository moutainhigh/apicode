package com.ycandyz.master.entities.mall.goodsManage;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @Description: 规格模版
 * @Author li Zhuo
 * @Date 2020/10/9
 * @Version: V1.0
*/

@Data
@TableName("mall_spec")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="mall_spec对象", description="规格模版表")
public class MallSpec{

    @TableId(type = IdType.UUID)
    @ApiModelProperty(value = "id")
    private long id;
    @ApiModelProperty(value = "商店编号")
    private String shopNo;
    @ApiModelProperty(value = "规格编号")
    private String specNo;
    @ApiModelProperty(value = "规格名称")
    private String specName;
    @ApiModelProperty(value = "id")
    private List<String> specValues;
    @ApiModelProperty(value = "排序值")
    private Integer sortValue;
    @ApiModelProperty(value = "状态：1: 正常，0: 无效  默认1")
    private Integer status;
    @ApiModelProperty(value = "createdTime")
    private Date createdTime;
    @ApiModelProperty(value = "updatedTime")
    private Date updatedTime;
}
