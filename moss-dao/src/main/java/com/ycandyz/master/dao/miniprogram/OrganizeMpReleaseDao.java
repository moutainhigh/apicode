package com.ycandyz.master.dao.miniprogram;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.dto.miniprogram.OrganizeMpReleaseDTO;
import com.ycandyz.master.entities.miniprogram.OrganizeMpRelease;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycandyz.master.vo.OrganizeMpReleaseParamVO;
import org.apache.ibatis.annotations.Param;

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

    Page<OrganizeMpReleaseDTO> listAll(Page pageQuery,@Param("organizeId") Long organizeId);

    List<OrganizeMpReleaseDTO>  selByOrganizeId(Long organizeId);

    void insertVersion(OrganizeMpReleaseParamVO organizeMpReleaseParamVO);

    void updateDraftTime(@Param("planId") Integer planId);
}
