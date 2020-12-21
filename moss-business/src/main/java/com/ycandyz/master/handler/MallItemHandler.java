package com.ycandyz.master.handler;

import com.ycandyz.master.abstracts.AbstractHandler;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.dao.mall.goodsManage.GoodsMallItemDao;
import com.ycandyz.master.domain.query.risk.ReviewParam;
import com.ycandyz.master.domain.response.risk.ContentReviewRep;
import com.ycandyz.master.dto.risk.ContentReviewDTO;
import com.ycandyz.master.entities.mall.goodsManage.MallItem;
import com.ycandyz.master.enums.ReviewEnum;
import com.ycandyz.master.enums.TabooOperateEnum;
import com.ycandyz.master.utils.EnumUtil;
import com.ycandyz.master.utils.MyCollectionUtils;
import com.ycandyz.master.utils.PatternUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


@Component
@Slf4j
public class MallItemHandler extends AbstractHandler {


    @Autowired
    private GoodsMallItemDao mallItemDao;

    @Override
    @Transactional
    public ReturnResponse examine(ReviewParam reviewParam) {
        if (reviewParam == null ){
            return ReturnResponse.success("商品详情更新数据为null");
        }
        String desc = EnumUtil.getByCode(TabooOperateEnum.class, reviewParam.getOper()).getDesc();
        Long id = reviewParam.getId();
        String contentId = contentreviewDao.selectById(reviewParam.getId());
        MallItem mallItem = mallItemDao.selByitemNo(contentId);
        if (mallItem ==null){
            String str=String.format("审核id为%d对应的商品详情id为%s数据不存在",id,contentId, desc);
            return  ReturnResponse.success(str);
        }
        int i = mallItemDao.updateOneMallItem(contentId,reviewParam.getOper());
        if (i > 0) {
            updateOrInsert(id,contentId, reviewParam.getType());
            log.info("商品详情id为{}的数据审批{}成功",contentId,desc);
            String str=String.format("商品详情id为%s的数据审批%s成功",contentId, desc);
            insertAllcontentReviewLog(id,contentId,1, reviewParam.getOper(),2);
            return ReturnResponse.success(str);
        }
        log.info("商品详情id为{}的数据审批{}失败",contentId,desc);
        String str=String.format("商品详情id为%s的数据审批%s失败",contentId, desc);
        return ReturnResponse.success(str);
    }

    @Override
    public void handleContentreview(ContentReviewDTO contentReviewDTO, ContentReviewRep contentReviewRep) {
        //m.item_name,m.item_text,m.banners,m.share_descr,m.share_img
//        @ApiModelProperty(value = "商品名称")
//        private String itemName;
//        @ApiModelProperty(value = "商品描述")
//        private String itemText;
//        @ApiModelProperty(value = "商品分享描述")
//        private String itemShareDescr;
//        @ApiModelProperty(value = "商品轮播图banners，商品分享图片shareImg")
//        private String[] itemImgUrls;
        if (contentReviewDTO != null && contentReviewDTO.getContent() != null){
            String content = String.valueOf(contentReviewDTO.getContent());
            BeanUtils.copyProperties(contentReviewDTO,contentReviewRep);
            contentReviewRep.setId(contentReviewDTO.getId());
            if (content != null){
                String[] split = content.split("︵",-1);
                contentReviewRep.setItemName(split[0]);
                String text = split[5];
                if (text != null){
                    Document document = Jsoup.parse(text);
                    Elements p = document.getElementsByTag("p");
                    contentReviewRep.setItemText(p.text());
                }
                contentReviewRep.setItemShareDescr(split[2]);
                String banner = split[1];
                List<String> list = MyCollectionUtils.parseIds(banner);
                List<String> itemImgUrls = new ArrayList<>();
                //获取富文本中图片
                List<String> imgStr = PatternUtils.getImgStr(text);
                if (imgStr != null){
                    itemImgUrls.addAll(imgStr);
                }
                list.stream()
                        .filter(s->StringUtils.isNotBlank(s.replace("\"", "")))
                        .forEach(s->{
                    itemImgUrls.add(s.replace("\"", ""));
                });
                contentReviewRep.setItemImgUrls(itemImgUrls);
                if (contentReviewDTO.getReviewAuditResult() == 2){
                    contentReviewRep.setAuditResult(Integer.valueOf(split[4]));
                }else {
                    contentReviewRep.setAuditResult(null);
                }
            }
        }
    }

    //批量通过/屏蔽
    @Override
    @Transactional
    public ReturnResponse handleExamine(Map<Integer,List<Long>> maps) {
        int oper = 0;
        List<Long> list = null;
        if (maps == null || maps.size() == 0){
            return ReturnResponse.success("商品详情无通过或屏蔽数据");
        }
        for(Map.Entry<Integer, List<Long>> vo : maps.entrySet()){
            oper = vo.getKey();
            list = vo.getValue();
        }
        String desc = EnumUtil.getByCode(TabooOperateEnum.class, oper).getDesc();
        AtomicInteger i = new AtomicInteger();
        for(Map.Entry<Integer, List<Long>> entry : maps.entrySet()){
            List<String> ids = new ArrayList<>();
            for (Long id: entry.getValue()) {
                String contentId = contentreviewDao.selectById(id);
                MallItem mallItem = mallItemDao.selByitemNo(contentId);
                if (mallItem ==null){
                    String str=String.format("审核id为%d对应的商品详情id为%s的数据不存在",id,contentId, desc);
                    return  ReturnResponse.success(str);
                }
                ids.add(contentId);
            }
            int i1 = mallItemDao.handleExamine(entry.getKey(),ids);
            i.set(i1);
        }
        if (i.get() == list.size()){
            String str=String.format("商品详情批量%s成功", desc);
            int finalOper = oper;
            maps.forEach((k,v)->{
                v.stream().forEach(id->{
                    String contentId = contentreviewDao.selectById(id);
                    updateOrInsert(id, contentId,1);
                    insertAllcontentReviewLog(id,contentId,0, finalOper,2);
                });
            });
            return ReturnResponse.success(str);
        }
        String str=String.format("商品详情批量%s失败", desc);
        return ReturnResponse.success(str);
    }

    @Override
    public void afterPropertiesSet() {
        HandlerContext.putHandler(ReviewEnum.MALLITEM.getCode(),this);
    }
}
