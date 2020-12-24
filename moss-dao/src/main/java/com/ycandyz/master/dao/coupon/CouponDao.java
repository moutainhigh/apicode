package com.ycandyz.master.dao.coupon;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.domain.query.coupon.CouponQuery;
import com.ycandyz.master.dto.coupon.CouponDetailDTO;
import com.ycandyz.master.entities.coupon.Coupon;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 优惠卷 Mapper 接口
 * </p>
 *
 * @since 2020-11-17
 */
public interface CouponDao extends BaseMapper<Coupon> {

    Page<CouponDetailDTO> queryTicketPageList(Page page, @Param("p") CouponQuery couponQuery);

    CouponDetailDTO queryTicketDetailByTicketNo(@Param("id") Long id, @Param("shopNo") String shopNo);
}
