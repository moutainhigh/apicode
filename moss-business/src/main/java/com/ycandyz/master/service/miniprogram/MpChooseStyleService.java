package com.ycandyz.master.service.miniprogram;

import com.ycandyz.master.model.miniprogram.OrganizeChooseMpConfigPage;
import com.ycandyz.master.model.miniprogram.OrganizeMpConfigMenuVO;

public interface MpChooseStyleService {

    OrganizeMpConfigMenuVO selById(Integer id);

    OrganizeMpConfigMenuVO selByOrGanizeMoudleId(Integer id);

    OrganizeChooseMpConfigPage selectMenuById(Integer menuid);
}
