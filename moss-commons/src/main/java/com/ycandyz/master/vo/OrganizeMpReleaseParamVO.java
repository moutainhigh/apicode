package com.ycandyz.master.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ycandyz.master.entities.miniprogram.OrganizeMpRelease;
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
public class OrganizeMpReleaseParamVO extends OrganizeMpRelease {

    @ApiModelProperty(value = "organizeId")
    private Long organizeId;


    @ApiModelProperty(value = "申请发布时间")
    private Date createTime;

    @ApiModelProperty(value = "编辑修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "版本")
    private String version;

}
