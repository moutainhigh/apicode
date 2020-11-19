package com.ycandyz.master.model.miniprogram;

import com.ycandyz.master.entities.miniprogram.OrganizeMpRelease;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * <p>
 * @Description 企业小程序发布记录 VO
 * </p>
 *
 * @author SanGang
 * @since 2020-11-16
 * @version 2.0
 */
@Getter
@Setter
@TableName("organize_mp_release")
public class OrganizeMpReleaseVO extends OrganizeMpRelease {

    @ApiModelProperty(value = "小程序名称")
    private String planName;

    @ApiModelProperty(value = "申请发布时间")
    private Date createTime;

    @ApiModelProperty(value = "编辑修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "版本")
    private String version;

}
