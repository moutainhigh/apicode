package com.ycandyz.master.dto.miniprogram;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ycandyz.master.entities.miniprogram.OrganizeMpRelease;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * <p>
 * @Description 企业小程序发布记录 实体类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-16
 * @version 2.0
 */
@Getter
@Setter
@TableName("organize_mp_release")
public class OrganizeMpReleaseDTO extends OrganizeMpRelease {
    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "小程序名称")
    private String planName;

    @ApiModelProperty(value = "小程序模板方案编号")
    private Integer planId;

    @ApiModelProperty(value = "申请发布时间")
    private Date createTime;

    @ApiModelProperty(value = "编辑修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "版本")
    private String version;
}
