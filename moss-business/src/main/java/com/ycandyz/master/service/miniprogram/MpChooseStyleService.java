package com.ycandyz.master.service.miniprogram;

import com.ycandyz.master.model.miniprogram.OrganizeChooseMpConfigPage;
import com.ycandyz.master.model.miniprogram.OrganizeMpConfigMenuVO;
import com.ycandyz.master.model.miniprogram.OrganizeMpConfigPageMenuVO;
import com.ycandyz.master.vo.OrganizeMenuMpRequestVO;
import com.ycandyz.master.vo.OrganizeMpRequestVO;

import java.util.List;

public interface MpChooseStyleService {

    List<OrganizeMpConfigMenuVO> selByOrGanizeMoudleId(Integer id);

    List<OrganizeMpConfigPageMenuVO> selectMenuById(Integer menuid);

    void saveSingle(OrganizeMenuMpRequestVO organizeMenuMpRequestVO);

    void saveAll(OrganizeMpRequestVO organizeMpRequestVO);

    Integer get();

    List<OrganizeMpConfigMenuVO> select();

}
