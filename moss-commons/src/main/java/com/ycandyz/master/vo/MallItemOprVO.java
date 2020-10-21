package com.ycandyz.master.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @Description: 规格模版
 * @Author li Zhuo
 * @Date 2020/10/9
 * @Version: V1.0
*/

@Data
public class MallItemOprVO {

    @ApiModelProperty(value = "商品编号列表")
    private String[] itemNoList;

    @ApiModelProperty(value = "10: 上架中，20: 已下架，30: 售罄，40: 仓库中，50: 删除")
    private Integer status;

}
