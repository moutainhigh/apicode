package com.ycandyz.master.domain.response.miniprogram;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ycandyz.master.entities.miniprogram.MpConfigModuleBase;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * @Description  Resp
 * </p>
 *
 * @author Wang Yang
 * @since 2020-11-15
 * @version 2.0
 */
@Data
@TableName("mp_config_module_base")
public class MpConfigModuleBaseResp implements Serializable {

    @ApiModelProperty(value = "元素标识")
    private Integer id;

    @ApiModelProperty(value = "元素标识")
    private String baseCode;

    @ApiModelProperty(value = "元素名称")
    private String baseName;

    @ApiModelProperty(value = "模块编号")
    private Integer sortBase;

    @ApiModelProperty(value = "显示样式")
    private Integer showLayout;

    @ApiModelProperty(value = "显示数量")
    private Integer displayNum;

    @ApiModelProperty(value = "元素图片")
    private String replacePicUrl;


}
