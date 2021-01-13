package com.ycandyz.master.service.mall.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycandyz.master.api.BasePageResult;
import com.ycandyz.master.base.BaseService;
import com.ycandyz.master.dao.mall.MallItemRecommendDao;
import com.ycandyz.master.dao.mall.MallItemRecommendRelationDao;
import com.ycandyz.master.dao.mall.MallItemRecommendSettingDao;
import com.ycandyz.master.domain.UserVO;
import com.ycandyz.master.domain.model.mall.MallItemRecommendModel;
import com.ycandyz.master.domain.query.mall.MallItemRecommendQuery;
import com.ycandyz.master.domain.response.mall.MallItemRecommendRelationResp;
import com.ycandyz.master.domain.response.mall.MallItemRecommendResp;
import com.ycandyz.master.entities.mall.MallItemRecommend;
import com.ycandyz.master.entities.mall.MallItemRecommendRelation;
import com.ycandyz.master.entities.mall.MallItemRecommendSetting;
import com.ycandyz.master.exception.BusinessException;
import com.ycandyz.master.service.mall.IMallItemRecommendService;
import com.ycandyz.master.utils.AssertUtils;
import com.ycandyz.master.utils.IDGeneratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.ycandyz.master.constant.DataConstant.*;

/**
 * @author WangWx
 * @version 2.0
 * @Description 商品推荐基础表 业务类
 * @since 2021-01-12
 */
@Slf4j
@Service
public class MallItemRecommendServiceImpl extends BaseService<MallItemRecommendDao, MallItemRecommend, MallItemRecommendQuery> implements IMallItemRecommendService {

    @Resource
    private MallItemRecommendSettingDao mallItemRecommendSettingDao;
    @Resource
    private MallItemRecommendRelationDao mallItemRecommendRelationDao;

    @Transactional(rollbackFor = Exception.class)
    public void updateRecommend(MallItemRecommendModel model) {
        UserVO user = getUser();
        QueryWrapper<MallItemRecommendSetting> queryWrapper = new QueryWrapper<>();
        String settingNo;
        List<MallItemRecommendSetting> settings = mallItemRecommendSettingDao.selectList(queryWrapper.eq("shop_no", user.getShopNo()).eq("item_recommend_no", model.getItemRecommendNo()));
        if (CollectionUtil.isNotEmpty(settings)) {
            MallItemRecommendSetting setting = settings.get(0);
            setting.setShowName(model.getShowName());
            setting.setIsRecommend(model.getIsRecommend());
            setting.setRecommendWay(model.getRecommendWay());
            setting.setRecommendType(model.getRecommendType());
            mallItemRecommendSettingDao.updateById(setting);
            UpdateWrapper<MallItemRecommendRelation> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("item_recommend_setting_no", setting.getItemRecommendSettingNo());
            updateWrapper.set("status", 0);
            mallItemRecommendRelationDao.update(new MallItemRecommendRelation(), updateWrapper);
            settingNo = setting.getItemRecommendSettingNo();
        } else {
            MallItemRecommendSetting t = new MallItemRecommendSetting();
            BeanUtils.copyProperties(model, t);
            t.setShopNo(user.getShopNo());
            t.setItemRecommendSettingNo(IDGeneratorUtils.getStrId());
            mallItemRecommendSettingDao.insert(t);
            settingNo = t.getItemRecommendSettingNo();
        }
        if (CollectionUtil.isNotEmpty(model.getRecommendItemList())) {
            List<String> recommendItemList = model.getRecommendItemList();
            recommendItemList.forEach(item -> {
                MallItemRecommendRelation relation = new MallItemRecommendRelation();
                relation.setItemNo(item);
                relation.setStatus(1);
                relation.setItemRecommendSettingNo(settingNo);
                relation.setItemRecommendRelationNo(IDGeneratorUtils.getStrId());
                mallItemRecommendRelationDao.insert(relation);
            });
        }
    }

    public MallItemRecommendResp getOne(String itemRecommendNo) {
        UserVO user = getUser();
        QueryWrapper<MallItemRecommend> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("item_recommend_no", itemRecommendNo);
        List<MallItemRecommend> mallItemRecommends = baseMapper.selectList(queryWrapper);
        MallItemRecommend mallItemRecommend = null;
        if (CollectionUtil.isNotEmpty(mallItemRecommends)) {
            mallItemRecommend = mallItemRecommends.get(0);
        }
        AssertUtils.notNull(mallItemRecommend, "该推荐设置已不再支持");
        if (MALL_ITEM_RECOMMEND_RECOMMEND_STATUS_INVALID == mallItemRecommend.getStatus()) {
            throw new BusinessException("该推荐设置已不再支持");
        }
        MallItemRecommendResp mallItemRecommendResp = new MallItemRecommendResp();
        BeanUtils.copyProperties(mallItemRecommend, mallItemRecommendResp);
        mallItemRecommendResp.setShowName("推荐");
        mallItemRecommendResp.setItemRecommendSettingNo("");
        List<MallItemRecommendSetting> settings = mallItemRecommendSettingDao.selectList(new QueryWrapper<MallItemRecommendSetting>().eq("shop_no", user.getShopNo()).eq("item_recommend_no", mallItemRecommendResp.getItemRecommendNo()));
        MallItemRecommendSetting setting = null;
        if (CollectionUtil.isNotEmpty(settings)) {
            setting = settings.get(0);
        }
        if (null != setting) {
            mallItemRecommendResp.setItemRecommendSettingNo(setting.getItemRecommendSettingNo());
            mallItemRecommendResp.setIsRecommend(setting.getIsRecommend());
            mallItemRecommendResp.setRecommendWay(setting.getRecommendWay());
            mallItemRecommendResp.setRecommendType(setting.getRecommendType());
            mallItemRecommendResp.setUpdatedTime(setting.getUpdatedTime());
            if (StringUtils.isNotEmpty(setting.getShowName())) {
                mallItemRecommendResp.setShowName(setting.getShowName());
            }
        }
        List<MallItemRecommendRelationResp> list = mallItemRecommendSettingDao.getList(setting.getItemRecommendSettingNo());
        mallItemRecommendResp.setRecommendItem(list);
        return mallItemRecommendResp;
    }

    public BasePageResult<MallItemRecommendResp> selectPage(Page page, MallItemRecommendQuery query) {
        UserVO user = getUser();
        String shopNo = user.getShopNo();
        QueryWrapper<MallItemRecommend> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1);
        if (StringUtils.isNotEmpty(query.getLocationName())) {
            queryWrapper.like("location_name", query.getLocationName());
        }
        queryWrapper.orderByAsc("id");
        Page<MallItemRecommend> recommendPages = (Page<MallItemRecommend>) baseMapper.selectPage(page, queryWrapper);
        List<MallItemRecommend> records = recommendPages.getRecords();
        List<MallItemRecommendResp> recommendResps = new ArrayList<>();
        records.forEach(vo -> {
            MallItemRecommendResp resp = new MallItemRecommendResp();
            BeanUtils.copyProperties(vo, resp);
            List<MallItemRecommendSetting> settings = mallItemRecommendSettingDao.selectList(new QueryWrapper<MallItemRecommendSetting>().eq("shop_no", shopNo).eq("item_recommend_no", vo.getItemRecommendNo()));
            resp.setRecommendContent("");
            resp.setItemRecommendSettingNo("");
            resp.setShowName(MALL_ITEM_RECOMMEND_SHOW_NAME);
            if (CollectionUtil.isNotEmpty(settings)) {
                MallItemRecommendSetting setting = settings.get(0);
                resp.setItemRecommendSettingNo(setting.getItemRecommendSettingNo());
                resp.setIsRecommend(setting.getIsRecommend());
                resp.setRecommendWay(setting.getRecommendWay());
                resp.setRecommendType(setting.getRecommendType());
                resp.setUpdatedTime(setting.getUpdatedTime());
                if (StringUtils.isNotEmpty(setting.getShowName())) {
                    resp.setShowName(setting.getShowName());
                }
                if (MALL_ITEM_RECOMMEND_IS_RECOMMEND_YES == setting.getIsRecommend()) {
                    if (MALL_ITEM_RECOMMEND_WAY_AUTO == setting.getRecommendWay()) {
                        resp.setRecommendContent(MALL_ITEM_RECOMMEND_RECOMMEND_TYPE_MAP.get(setting.getRecommendType()));
                    } else {
                        if (StringUtils.isNotEmpty(setting.getItemRecommendSettingNo())) {
                            List<MallItemRecommendRelationResp> list = mallItemRecommendSettingDao.getList(setting.getItemRecommendSettingNo());
                            List<String> itemNames = new ArrayList<>();
                            list.forEach(relationResp -> itemNames.add(relationResp.getItemName()));
                            if (CollectionUtil.isNotEmpty(itemNames)) {
                                String join = StringUtils.join(itemNames, ",");
                                resp.setRecommendContent(join);
                            }
                        }
                    }
                }
            }
            recommendResps.add(resp);
        });
        Page<MallItemRecommendResp> resultPage = new Page<>();
        resultPage.setRecords(recommendResps);
        resultPage.setPages(recommendPages.getPages());
        resultPage.setCurrent(recommendPages.getCurrent());
        resultPage.setOrders(recommendPages.getOrders());
        resultPage.setSize(recommendPages.getSize());
        resultPage.setTotal(recommendPages.getTotal());
        return new BasePageResult<>(resultPage);
    }

}
