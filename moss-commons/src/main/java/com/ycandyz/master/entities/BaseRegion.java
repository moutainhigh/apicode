package com.ycandyz.master.entities;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @Description: 省市表
 * @Author li Zhuo
 * @Date 2020/10/9
 * @Version: V1.0
*/
@Data
@TableName("base_region")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="base_region对象", description="省市表")
public class BaseRegion {

    @ApiModelProperty(value = "区域编码")
    private Integer regionCode;
    @ApiModelProperty(value = "区域名称")
    private String regionName;

}
