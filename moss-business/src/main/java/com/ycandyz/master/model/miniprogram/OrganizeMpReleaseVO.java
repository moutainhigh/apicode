package com.ycandyz.master.model.miniprogram;

import com.ycandyz.master.entities.miniprogram.OrganizeMpRelease;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

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

}
