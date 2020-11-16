package com.ycandyz.master.domain.response.miniprogram;

import com.ycandyz.master.annotation.Condition;
import com.ycandyz.master.entities.miniprogram.OrganizeMpRelease;
import com.baomidou.mybatisplus.annotation.TableName;

import com.ycandyz.master.enums.ConditionEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * <p>
 * @Description 企业小程序发布记录 Resp
 * </p>
 *
 * @author SanGang
 * @since 2020-11-16
 * @version 2.0
 */
@Getter
@Setter
@TableName("organize_mp_release")
public class OrganizeMpReleaseResp extends OrganizeMpRelease {
    @ApiModelProperty(value = "编号")
    @Condition(field = "id", condition = ConditionEnum.EQ)
    private Integer id;

    @ApiModelProperty(value = "小程序名称")
    private String planName;

    @ApiModelProperty(value = "小程序模板方案编号")
    private Integer planId;

    @ApiModelProperty(value = "申请发布时间起")
    @Condition(field = "create_time", condition = ConditionEnum.GE)
    private Date createTimeS;

    @ApiModelProperty(value = "申请发布时间止")
    @Condition(field = "create_time", condition = ConditionEnum.LE)
    private Date createTimeE;

    @ApiModelProperty(value = "编辑修改时间起")
    @Condition(field = "update_time", condition = ConditionEnum.GE)
    private Date updateTimeS;

    @ApiModelProperty(value = "编辑修改时间止")
    @Condition(field = "update_time", condition = ConditionEnum.LE)
    private Date updateTimeE;

    @ApiModelProperty(value = "企业类目")
    private String organizeCategory;

}
