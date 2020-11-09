package com.ycandyz.master.handler;

import com.ycandyz.master.abstracts.AbstractHandler;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.dao.mall.goodsManage.MallItemDao;
import com.ycandyz.master.domain.query.risk.ReviewParam;
import com.ycandyz.master.domain.response.risk.ContentReviewRep;
import com.ycandyz.master.dto.risk.ContentReviewDTO;
import com.ycandyz.master.enums.ReviewEnum;
import com.ycandyz.master.enums.TabooOperateEnum;
import com.ycandyz.master.utils.EnumUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


@Component
@Slf4j
public class MallItemHandler extends AbstractHandler {


    @Autowired
    private MallItemDao mallItemDao;

    @Override
    @Transactional
    public ReturnResponse examine(ReviewParam reviewParam) {
        if (reviewParam == null ){
            return ReturnResponse.success("商品详情更新数据为null");
        }
        String desc = EnumUtil.getByCode(TabooOperateEnum.class, reviewParam.getOper()).getDesc();
        Long id = reviewParam.getContentId();
        int i = mallItemDao.updateOneMallItem(String.valueOf(reviewParam.getContentId()),reviewParam.getOper());
        if (i > 0) {
            updateOrInsert(reviewParam.getContentId(), reviewParam.getType());
            log.info("商品详情id为{}的数据审批{}成功",id,desc);
            String str=String.format("商品详情id为%d的数据审批%s成功",id, desc);
            return ReturnResponse.success(str);
        }
        log.info("商品详情id为{}的数据审批{}成功",id,desc);
        String str=String.format("商品详情id为%d的数据审批%s成功",id, desc);
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
            contentReviewRep.setItemNo(contentReviewDTO.getContentId());
            if (content != null){
                String[] split = content.split(";",-1);
                if (content.startsWith(";")){
                    contentReviewRep.setItemName(null);
                    contentReviewRep.setItemText(split[0]);
                    contentReviewRep.setItemShareDescr(split[2]);
                    String banner = split[2];
                    String[] banners = banner.split(",",-1);
                    String[] itemImgUrls = null;
                    if ( banners != null){
                        int length = banners.length + 1;
                        itemImgUrls = new String[length];
                        for (int i = 0; i < length - 1; i++) {
                            itemImgUrls[i] = banners[i];
                        }
                        itemImgUrls[length -1 ] = split[3];
                    }
                    contentReviewRep.setItemImgUrls(itemImgUrls);
                }else {
                    contentReviewRep.setItemName(split[0]);
                    contentReviewRep.setItemText(split[1]);
                    contentReviewRep.setItemShareDescr(split[3]);
                    String banner = split[2];
                    String[] banners = banner.split(",",-1);
                    String[] itemImgUrls = null;
                    if ( banners != null){
                        int length = banners.length + 1;
                        itemImgUrls = new String[length];
                        for (int i = 0; i < length - 1; i++) {
                            itemImgUrls[i] = banners[i];
                        }
                        itemImgUrls[length -1 ] = split[4];
                    }
                    contentReviewRep.setItemImgUrls(itemImgUrls);
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
        maps.forEach((k,v)->{
            List<String> stringList = new ArrayList<>();
            v.stream().forEach(s->stringList.add(String.valueOf(s)));
            int i1 = mallItemDao.handleExamine(k,stringList);
            i.set(i1);
        });
        if (i.get() == list.size()){
            String str=String.format("商品详情批量%s成功", desc);
            return ReturnResponse.success(str);
        }
        String str=String.format("商品详情批量%s成功", desc);
        return ReturnResponse.success(str);
    }

    @Override
    public void afterPropertiesSet() {
        HandlerContext.putHandler(ReviewEnum.MALLITEM.getCode(),this);
    }
}
