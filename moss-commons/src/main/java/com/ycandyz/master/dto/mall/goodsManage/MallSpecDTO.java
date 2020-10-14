package com.ycandyz.master.dto.mall.goodsManage;

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
public class MallSpecDTO {

    @ApiModelProperty(value = "规格编号")
    private String specNo;
    @ApiModelProperty(value = "规格名称")
    private String specName;
    @ApiModelProperty(value = "id")
    private String specValues;
}
