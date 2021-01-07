package com.ycandyz.master.dao.mall;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.domain.query.mall.MallItemBaseQuery;
import com.ycandyz.master.domain.query.mall.MallItemQuery;
import com.ycandyz.master.domain.response.mall.MallItemBaseResp;
import com.ycandyz.master.domain.response.mall.MallItemPageResp;
import com.ycandyz.master.domain.response.mall.MallItemResp;
import com.ycandyz.master.dto.mall.MallItemDTO;
import com.ycandyz.master.entities.mall.MallItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author SanGang
 * @since 2020-10-13
 */
public interface MallItemHomeDao extends BaseMapper<MallItem> {


    Page<MallItemBaseResp> selectMallItemPage(Page page, @Param("query") MallItemBaseQuery query);

    /**
     * 根据规格编号查询商品信息
     * @param skuNos
     * @return
     */
    List<MallItemDTO> selectItemSku(@Param("skuNos") List<String> skuNos);

    MallItem selectMallItemById(Long id);


    /**
     * 按库存编号修改
     * @param itemNo
     * @param stock
     * @return
     */
    int updateMallSkuByItemNo(@Param("itemNo") String itemNo,@Param("stock") Integer stock);


    int updateBuyNumByItemNo(@Param("itemNo") String itemNo);


    /**
     * 通过购物车订单编号，查询商品集合
     * @param orderSn
     * @return
     */
    List<MallItemDTO> selectMallItemByCartOrderSn(@Param("orderSn") String orderSn);
    Page<MallItemBaseResp> selectMallItemPageByCouponId(Page page, @Param("query") MallItemBaseQuery query);

    Page<MallItemPageResp> getMallItemPage(Page page, @Param("q") MallItemQuery query);

    int updateByItemNo(MallItem entity);

    int updateOrgByItemNo(MallItem entity);
}
