package com.ycandyz.master.service.miniprogram;

import com.ycandyz.master.model.miniprogram.OrganizeMpConfigMenuVO;
import com.ycandyz.master.model.miniprogram.OrganizeMpConfigPageSingleMenuVO;
import com.ycandyz.master.vo.OrganizeMenuMpRequestVO;

import java.util.List;

public interface MpChooseStyleService {

    List<OrganizeMpConfigMenuVO> selByOrGanizeMoudleId(Integer id);

    OrganizeMpConfigPageSingleMenuVO selectMenuById(Integer menuid);

    void saveSinglePage(OrganizeMenuMpRequestVO organizeMenuMpRequestVO);

    Integer get();

    List<OrganizeMpConfigMenuVO> select();

    List<OrganizeMpConfigMenuVO> select2();

    void saveDraftOrPublish(OrganizeMenuMpRequestVO organizeMenuMpRequestVO);

    void del();

}
