package com.ycandyz.master.service.miniprogram.impl;

import com.ycandyz.master.entities.miniprogram.MpConfigMenu;
import com.ycandyz.master.domain.query.miniprogram.MpConfigMenuQuery;
import com.ycandyz.master.dao.miniprogram.MpConfigMenuDao;
import com.ycandyz.master.service.miniprogram.IMpConfigMenuService;
import com.ycandyz.master.controller.base.BaseService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * @Description 小程序配置-菜单配置 业务类
 * </p>
 *
 * @author WangYang
 * @since 2020-11-13
 * @version 2.0
 */
@Slf4j
@Service
public class MpConfigMenuServiceImpl extends BaseService<MpConfigMenuDao,MpConfigMenu,MpConfigMenuQuery> implements IMpConfigMenuService {

}
