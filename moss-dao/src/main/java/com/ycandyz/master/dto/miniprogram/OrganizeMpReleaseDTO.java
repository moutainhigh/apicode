package com.ycandyz.master.dto.miniprogram;

import com.ycandyz.master.entities.miniprogram.OrganizeMpRelease;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

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

}
