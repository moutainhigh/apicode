package com.ycandyz.master.dao.miniprogram;

import com.ycandyz.master.dto.miniprogram.OrganizeMpReleaseDTO;
import com.ycandyz.master.entities.miniprogram.OrganizeMpRelease;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 企业小程序发布记录 Mapper 接口
 * </p>
 *
 * @author SanGang
 * @since 2020-11-16
 */
public interface OrganizeMpReleaseDao extends BaseMapper<OrganizeMpRelease> {

    List<OrganizeMpReleaseDTO> listAll();

    List<OrganizeMpReleaseDTO>  selByOrganizeId(Long organizeId);
}
