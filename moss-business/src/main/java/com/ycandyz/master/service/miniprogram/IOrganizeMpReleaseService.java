package com.ycandyz.master.service.miniprogram;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.entities.miniprogram.OrganizeMpRelease;
import com.ycandyz.master.model.miniprogram.OrganizeMpReleaseVO;

/**
 * <p>
 * @Description 企业小程序发布记录 业务接口类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-16
 * @version 2.0
 */
public interface IOrganizeMpReleaseService extends IService<OrganizeMpRelease>{

    Page<OrganizeMpReleaseVO> listAll(Long page_size, Long page);

}
