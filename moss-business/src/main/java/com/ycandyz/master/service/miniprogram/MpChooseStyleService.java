package com.ycandyz.master.service.miniprogram;

import com.ycandyz.master.model.miniprogram.OrganizeChooseMpConfigPage;
import com.ycandyz.master.model.miniprogram.OrganizeMpConfigMenuVO;
import com.ycandyz.master.vo.OrganizeMenuMpRequestVO;
import com.ycandyz.master.vo.OrganizeMpRequestVO;

import java.util.List;

public interface MpChooseStyleService {

    List<OrganizeMpConfigMenuVO> selByOrGanizeMoudleId(Integer id);

    OrganizeChooseMpConfigPage selectMenuById(Integer menuid);

    Integer saveSingle(OrganizeMenuMpRequestVO organizeMenuMpRequestVO);

    void saveAll(OrganizeMpRequestVO organizeMpRequestVO);

    Integer get(Integer id);

    List<OrganizeMpConfigMenuVO> select();

}
