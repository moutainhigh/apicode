package com.ycandyz.master.dao.mall;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.domain.response.mall.MallShopResp;
import com.ycandyz.master.dto.mall.MallShopDTO;
import com.ycandyz.master.entities.mall.MallShop;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MallShopDao extends BaseMapper<MallShop> {

    MallShopDTO queryByShopNo(@Param("shopNo") String shopNo);

    /**
     * 通过企业id集合查询门店集合
     * @param organizeIds
     * @return
     */
    List<MallShopDTO> queryByOrganizeIdList(@Param("list") List<Integer> organizeIds);

    /**
     * 获取集团下所有企业门店
     * @param organizeId
     * @return 门店
     */
    Page<MallShopResp> getByOrganizeId(Page<MallShop> page,@Param("organizeId") Long organizeId);

    Page<MallShopResp> getByItemNo(Page<MallShop> page, @Param("itemNo") String itemNo);

    List<MallShopResp> getByItemNo(@Param("itemNo") String itemNo);

    List<MallShopResp> getByOrganizeId(@Param("organizeId") Long organizeId);

}
