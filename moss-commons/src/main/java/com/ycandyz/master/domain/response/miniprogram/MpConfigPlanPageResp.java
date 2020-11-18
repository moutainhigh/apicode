package com.ycandyz.master.domain.response.miniprogram;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ycandyz.master.entities.miniprogram.MpConfigPlanPage;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * @Description 小程序配置-页面配置 Resp
 * </p>
 *
 * @author Wang Yang
 * @since 2020-11-15
 * @version 2.0
 */
@Data
@TableName("mp_config_plan_page")
public class MpConfigPlanPageResp implements Serializable {

    @ApiModelProperty(value = "方案编号")
    private Integer id;

    @ApiModelProperty(value = "菜单编号")
    private Integer menuId;

    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @ApiModelProperty(value = "模块编号")
    private Integer moduleId;

    @ApiModelProperty(value = "模块名称")
    private String moduleName;

    @ApiModelProperty(value = "菜单模块排序")
    private Integer sortModule;

    @ApiModelProperty(value = "显示个数")
    private Integer displayNum;

    @ApiModelProperty(value = "元素信息")
    private List<MpConfigModuleBaseResp> baseInfo;

}
