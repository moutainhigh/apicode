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
    @JsonProperty(value = "menu_id")
    private Integer menuId;

    @ApiModelProperty(value = "菜单名称")
    @JsonProperty(value = "menu_name")
    private String menuName;

    @ApiModelProperty(value = "模块编号")
    @JsonProperty(value = "module_id")
    private Integer moduleId;

    @ApiModelProperty(value = "模块名称")
    @JsonProperty(value = "module_name")
    private String moduleName;

    @ApiModelProperty(value = "菜单模块排序")
    @JsonProperty(value = "sort_module")
    private Integer sortModule;

    @ApiModelProperty(value = "显示个数")
    @JsonProperty(value = "display_num")
    private Integer displayNum;

    @ApiModelProperty(value = "元素信息")
    @JsonProperty(value = "base_info")
    private List<MpConfigModuleBaseResp> baseInfo;

}
