package com.ycandyz.master.service.coupon.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.BasePageResult;
import com.ycandyz.master.api.CommonResult;
import com.ycandyz.master.api.PageModel;
import com.ycandyz.master.dao.coupon.CouponDao;
import com.ycandyz.master.dao.coupon.CouponDetailDao;
import com.ycandyz.master.dao.coupon.CouponDetailItemDao;
import com.ycandyz.master.dao.mall.MallHomeItemDao;
import com.ycandyz.master.dao.mall.goodsManage.MallCategoryDao;
import com.ycandyz.master.domain.UserVO;
import com.ycandyz.master.domain.query.coupon.CouponDetailQuery;
import com.ycandyz.master.domain.query.coupon.CouponQuery;
import com.ycandyz.master.domain.query.mall.MallItemQuery;
import com.ycandyz.master.domain.response.mall.MallItemResp;
import com.ycandyz.master.dto.coupon.CouponDetailDTO;
import com.ycandyz.master.dto.mall.MallCategoryDTO;
import com.ycandyz.master.entities.coupon.Coupon;
import com.ycandyz.master.entities.coupon.CouponDetail;
import com.ycandyz.master.entities.coupon.CouponDetailItem;
import com.ycandyz.master.entities.mall.goodsManage.MallCategory;
import com.ycandyz.master.model.coupon.CouponDetailVO;
import com.ycandyz.master.model.mall.MallCategoryVO;
import com.ycandyz.master.service.coupon.ICouponService;
import com.ycandyz.master.controller.base.BaseService;

import com.ycandyz.master.utils.IDGeneratorUtils;
import com.ycandyz.master.vo.MallItemVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * @Description 优惠卷 业务类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */
@Slf4j
@Service
public class CouponServiceImpl extends BaseService<CouponDao,Coupon,CouponQuery> implements ICouponService {

    @Autowired
    private CouponDao couponDao;

    @Autowired
    private CouponDetailDao couponDetailDao;

    @Autowired
    private CouponDetailItemDao couponDetailItemDao;

    @Autowired
    private MallCategoryDao mallCategoryDao;

    @Autowired
    private MallHomeItemDao mallHomeItemDao;

    @Override
    public CommonResult<BasePageResult<CouponDetailVO>> selectPageList(PageModel pageModel, CouponQuery couponQuery) {
        UserVO userVO = getUser();  //当前登陆用户
        if (userVO==null || userVO.getShopNo()==null || "".equals(userVO.getShopNo())){
            return CommonResult.failed("无门店信息");
        }
        BasePageResult<CouponDetailVO> infoVOPage = new BasePageResult<>();
        List<CouponDetailVO> list = new ArrayList<>();
        couponQuery.setShopNo(userVO.getShopNo());
        Page pageQuery = new Page(pageModel.getPage(), pageModel.getPageSize());
        Page<CouponDetailDTO> page = null;
        try {
            page = couponDao.queryTicketPageList(pageQuery, couponQuery);
            CouponDetailVO couponDetailVO = null;
            if (page.getRecords() != null && page.getRecords().size() > 0) {
                for (CouponDetailDTO couponDetailDTO : page.getRecords()){
                    couponDetailVO = new CouponDetailVO();
                    BeanUtils.copyProperties(couponDetailDTO, couponDetailVO);
                    list.add(couponDetailVO);
                }
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        infoVOPage.setTotal(page.getTotal());
        infoVOPage.setPageSize(page.getSize());
        infoVOPage.setResult(list);
        infoVOPage.setPage(page.getCurrent());
        return CommonResult.success(infoVOPage);
    }

    @Override
    public CommonResult<String> auditState(Long id, Integer state) {
        UserVO userVO = getUser();  //当前登陆用户
        Coupon coupon = couponDao.selectById(id);
        if (coupon==null){
            return CommonResult.failed("传入id无法查询到优惠券");
        }
        if (!userVO.getShopNo().equals(coupon.getShopNo())){
            return CommonResult.failed("当前用户登陆门店无权进行此操作");
        }
        if (coupon.getStatus()==state){
            //当前状态等于传入的状态
            return CommonResult.failed("无需重复操作");
        }
        coupon.setStatus(state);
        coupon.setUpdateTime(new Date());
        int flag = couponDao.updateById(coupon);
        if (flag>0){
            return CommonResult.success("成功");
        }
        return CommonResult.failed("更新失败");
    }

    @Override
    public CommonResult<CouponDetailVO> ticketDetail(Long id) {
        UserVO userVO = getUser();  //当前登陆用户
        CouponDetailDTO couponTicketInfoDTO = couponDao.queryTicketDetailByTicketNo(id,userVO.getShopNo());
        CouponDetailVO couponTicketInfoVO = null;
        if (couponTicketInfoDTO!=null){
            couponTicketInfoVO = new CouponDetailVO();
            BeanUtils.copyProperties(couponTicketInfoDTO,couponTicketInfoVO);
        }
        return CommonResult.success(couponTicketInfoVO);
    }

    @Transactional
    @Override
    public CommonResult<String> updateTicket(CouponDetailQuery couponDetailQuery){
        UserVO userVO = getUser();  //当前登陆用户
        //更新ticket表
        Coupon coupon = couponDao.selectById(couponDetailQuery.getId());
        if (coupon!=null){
            if (!coupon.getShopNo().equals(userVO.getShopNo())){
                return CommonResult.failed("当前所在门店无权进行此操作");
            }
            //获取优惠卷详情
            CouponDetail couponDetail = couponDetailDao.selectOne(new QueryWrapper<CouponDetail>().eq("coupon_id", couponDetailQuery.getId()));
            if (couponDetail!=null){
                String ticketInfoNo = couponDetail.getCouponDetailNo();   //优惠券详情编号
                //获取优惠券关联商品列表
                List<CouponDetailItem> couponDetailItems = couponDetailItemDao.selectList(new QueryWrapper<CouponDetailItem>().eq("ticket_info_no",couponDetail.getId()));
                List<String> infoItemNoList = null;
                if (couponDetailItems!=null && couponDetailItems.size()>0){
                    infoItemNoList = couponDetailItems.stream().map(CouponDetailItem::getItemNo).collect(Collectors.toList());
                }
                if (!Objects.equals(couponDetail.getBeginTime(),couponDetailQuery.getBeginTime()) ||
                        !Objects.equals(couponDetail.getEndTime(),couponDetailQuery.getEndTime()) ||
                        !Objects.equals(couponDetail.getShopType(),couponDetailQuery.getShopType()) ||
                        !Objects.equals(couponDetail.getUseType(),couponDetailQuery.getUserType()) ||
                        !Objects.equals(couponDetail.getFullMoney(),couponDetailQuery.getFullMoney()) ||
                        !Objects.equals(couponDetail.getDiscountMoney(),couponDetailQuery.getDiscountMoney()) ||
                        !Objects.equals(couponDetail.getValidityType(),couponDetailQuery.getValidityType()) ||
                        !Objects.equals(couponDetail.getDays(),couponDetailQuery.getDays()) ||
                        !Objects.equals(couponDetail.getUserType(),couponDetailQuery.getUserType()) ||
                        !Objects.equals(couponDetail.getTakeNum(),couponDetailQuery.getTakeNum()) ||
                        !Objects.equals(couponDetail.getSuperposition(),couponDetailQuery.getSuperposition()) ||
                        !Objects.equals(couponDetail.getObtain(),couponDetailQuery.getObtain()) ||
                        !Objects.equals(couponDetail.getRemark(),couponDetailQuery.getRemark()) ||
                        !Objects.equals(infoItemNoList,couponDetailQuery.getItemNoList())){
                    //修改以前的详情为过去时
                    CouponDetail oldInfo = couponDetailDao.selectOne(new QueryWrapper<CouponDetail>().eq("coupon_id",couponDetailQuery.getId()).eq("status",1));
                    if (oldInfo!=null){
                        oldInfo.setStatus(0);
                        couponDetailDao.updateById(oldInfo);
                    }
                    //有不满足的进入到优惠券详情修改相关代码
                    CouponDetail ticketInfo = new CouponDetail();
                    ticketInfo.setBeginTime(couponDetailQuery.getBeginTime());
                    ticketInfo.setEndTime(couponDetailQuery.getEndTime());
                    ticketInfo.setCreateTime(new Date());
                    ticketInfo.setDays(couponDetailQuery.getDays());
                    ticketInfo.setDiscountMoney(couponDetailQuery.getDiscountMoney());
                    ticketInfo.setFullMoney(couponDetailQuery.getFullMoney());
                    ticketInfo.setName(couponDetail.getName());
                    ticketInfo.setObtain(couponDetailQuery.getObtain());
                    ticketInfo.setRemark(couponDetailQuery.getRemark());
                    ticketInfo.setShopType(couponDetailQuery.getShopType());
                    ticketInfo.setValidityType(couponDetailQuery.getValidityType());
                    ticketInfo.setSuperposition(couponDetailQuery.getSuperposition());
                    ticketInfo.setTakeNum(couponDetailQuery.getTakeNum());
                    ticketInfo.setCouponDetailNo(String.valueOf(IDGeneratorUtils.getLongId()));
                    ticketInfo.setUpdateTime(new Date());
                    ticketInfo.setUserType(couponDetailQuery.getUserType());
                    ticketInfo.setUseType(couponDetailQuery.getUseType());
                    ticketInfo.setCouponId(couponDetail.getCouponId());
                    ticketInfo.setStatus(1);
                    couponDetailDao.insert(ticketInfo);
                }

                //最后更新优惠券表
                coupon.setUpdateTime(new Date());
                coupon.setName(couponDetailQuery.getName());
                coupon.setCouponSum(couponDetailQuery.getCouponSum());
                couponDao.updateById(coupon);
            }
            return CommonResult.success("更新成功!");
        }
        return CommonResult.failed("更新失败");
    }

    @Transactional
    @Override
    public CommonResult<String> insertTicket(CouponDetailQuery couponDetailQuery) {
        UserVO userVO = getUser();  //当前登陆用户
        Date current = new Date();
        String ticketInfoNo = String.valueOf(IDGeneratorUtils.getLongId());
        String ticketNo = String.valueOf(IDGeneratorUtils.getLongId());

        //新增优惠券
        Coupon couponTicket = new Coupon();
        couponTicket.setUpdateTime(current);
        couponTicket.setName(couponDetailQuery.getName());
        couponTicket.setCouponSum(couponDetailQuery.getCouponSum());
        couponTicket.setShopNo(userVO.getShopNo());
        couponTicket.setCouponNo(ticketNo);
        couponTicket.setStatus(1);
        couponDao.insert(couponTicket);

        //新增优惠券详情
        CouponDetail ticketInfo = new CouponDetail();
        ticketInfo.setBeginTime(couponDetailQuery.getBeginTime());
        ticketInfo.setEndTime(couponDetailQuery.getEndTime());
        ticketInfo.setCreateTime(new Date());
        ticketInfo.setDays(couponDetailQuery.getDays());
        ticketInfo.setDiscountMoney(couponDetailQuery.getDiscountMoney());
        ticketInfo.setFullMoney(couponDetailQuery.getFullMoney());
        ticketInfo.setName(couponDetailQuery.getName());
        ticketInfo.setObtain(couponDetailQuery.getObtain());
        ticketInfo.setRemark(couponDetailQuery.getRemark());
        ticketInfo.setShopType(couponDetailQuery.getShopType());
        ticketInfo.setValidityType(couponDetailQuery.getValidityType());
        ticketInfo.setSuperposition(couponDetailQuery.getSuperposition());
        ticketInfo.setTakeNum(couponDetailQuery.getTakeNum());
        ticketInfo.setCouponDetailNo(ticketInfoNo);
        ticketInfo.setUpdateTime(current);
        ticketInfo.setUserType(couponDetailQuery.getUserType());
        ticketInfo.setUseType(couponDetailQuery.getUseType());
        ticketInfo.setCouponId(couponTicket.getId());
        int flag = couponDetailDao.insert(ticketInfo);
        if (flag>0){
            return CommonResult.success("新增成功");
        }
        return CommonResult.failed("新增失败");
    }

    @Override
    public CommonResult<List<MallCategoryVO>> getCategoryList() {
        UserVO userVO = getUser();
        List<MallCategoryDTO> list = mallCategoryDao.selectAllByShopNo(userVO.getShopNo());
        if (list!=null && list.size()>0){
            List<MallCategoryVO> vos = categoryTree(list,null);
            return CommonResult.success(vos);
        }
        return CommonResult.success(null);
    }

    private List<MallCategoryVO> categoryTree(List<MallCategoryDTO> mallCategoryDTOS, String parentNo){
        List<MallCategoryVO> list = new ArrayList<>();
        MallCategoryVO mallCategoryVO = null;
        for (MallCategoryDTO mallCategoryDTO : mallCategoryDTOS){
            if (mallCategoryDTO.getParentCategoryNo()==parentNo){
                mallCategoryVO = new MallCategoryVO();
                BeanUtils.copyProperties(mallCategoryDTO,mallCategoryVO);
                mallCategoryVO.setChaildList(categoryTree(mallCategoryDTOS, mallCategoryVO.getCategoryNo()));
                list.add(mallCategoryVO);
            }
        }
        return list;
    }

    @Override
    public CommonResult<BasePageResult<MallItemVO>> itemList(Long id, Long page, Long pageSize, String type, String keyword, String category) {
        BasePageResult<MallItemVO> basePageResult = new BasePageResult<>();
        basePageResult.setPage(page);
        basePageResult.setPageSize(pageSize);
        UserVO userVO = getUser();
        Page page1 = new Page();
        page1.setPages(page);
        page1.setSize(pageSize);
        MallItemQuery mallItemQuery = new MallItemQuery();
        mallItemQuery.setShopNo(userVO.getShopNo());
        mallItemQuery.setCategoryNo(category);
        mallItemQuery.setItemName(keyword);
        Page<MallItemResp> mallItemRespPage = null;
        if (type.equals("all")){
            mallItemRespPage = mallHomeItemDao.selectMallItemPage(page1,mallItemQuery);
        }else if (type.equals("choose")){
            mallItemQuery.setCouponId(id);
            mallItemRespPage = mallHomeItemDao.selectMallItemPageByCouponId(page1,mallItemQuery);
        }
        List<MallItemVO> list = new ArrayList<>();
        if (mallItemRespPage!=null && mallItemRespPage.getRecords()!=null && mallItemRespPage.getRecords().size()>0){
            //获取所有分类集合
            List<MallCategory> mallCategoryList = mallCategoryDao.selectList(new QueryWrapper<MallCategory>().eq("shop_no",userVO.getShopNo()).eq("status",1));
            MallItemVO mallItemVO = null;
            for (MallItemResp mallItemResp : mallItemRespPage.getRecords()){
                mallItemVO = mallItemRespToVO(mallItemResp);
                mallItemVO.setCategoryName(getTreeCategoryName(mallCategoryList,mallItemVO.getCategoryNo()));
                list.add(mallItemVO);
            }
            basePageResult.setTotal(mallItemRespPage.getTotal());
            basePageResult.setResult(list);
        }
        return CommonResult.success(basePageResult);
    }

    /**
     * 数据库查询出的数据类型转换
     * @param mallItemResp
     * @return
     */
    private MallItemVO mallItemRespToVO(MallItemResp mallItemResp){
        MallItemVO mallItemVO = new MallItemVO();
        mallItemVO.setItemName(mallItemResp.getItemName());
        mallItemVO.setItemNo(mallItemResp.getItemNo());
        mallItemVO.setCategoryNo(mallItemResp.getCategoryNo());
        mallItemVO.setItemCover(mallItemResp.getItemCover());
        mallItemVO.setItemText(mallItemResp.getItemText());
        mallItemVO.setPrice(mallItemResp.getPrice());
        mallItemVO.setStock(mallItemResp.getStock());
        mallItemVO.setGoodsNo(mallItemResp.getGoodsNo());
        return mallItemVO;
    }

    /**
     * 获取当前分类的名称和所有父分类的名称拼接
     * @param list
     * @param categoryNo
     * @return
     */
    private String getTreeCategoryName(List<MallCategory> list, String categoryNo){
        if (list!=null && list.size()>0){
            Map<String, MallCategory> map = list.stream().collect(Collectors.toMap(MallCategory::getCategoryNo, a -> a,(k1,k2)->k1));
            if (map.get(categoryNo)!=null){
                MallCategory nowCategory = map.get(categoryNo);
                String name = recursionNo(map,nowCategory.getParentCategoryNo());
                return name+">"+nowCategory.getCategoryName();
            }
        }
        return null;
    }

    /**
     * 递归获取当前分类的名称和所有父分类的名称
     * @param map
     * @param parentNo
     * @return
     */
    private String recursionNo(Map<String, MallCategory> map, String parentNo){
        String name = "";
        if (parentNo!=null){
            MallCategory mallCategory = map.get(parentNo);
            if (mallCategory!=null) {
                name = recursionNo(map,mallCategory.getParentCategoryNo())+">"+mallCategory.getCategoryName();
            }
        }
        return name;
    }
}
