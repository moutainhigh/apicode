package com.ycandyz.master.handler;

import com.ycandyz.master.abstracts.AbstractHandler;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.dao.footprint.FootprintDao;
import com.ycandyz.master.dao.risk.ContentreviewDao;
import com.ycandyz.master.domain.query.footprint.FootprintQuery;
import com.ycandyz.master.domain.query.risk.ReviewParam;
import com.ycandyz.master.domain.response.risk.ContentReviewRep;
import com.ycandyz.master.dto.risk.ContentReviewDTO;
import com.ycandyz.master.entities.mall.goodsManage.MallItem;
import com.ycandyz.master.enums.ReviewEnum;
import com.ycandyz.master.enums.TabooOperateEnum;
import com.ycandyz.master.utils.EnumUtil;
import com.ycandyz.master.utils.PatternUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


@Component
@Slf4j
public class FootprintHandler extends AbstractHandler {

    @Autowired
    private FootprintDao footprintDao;

    @Autowired
    private ContentreviewDao contentreviewDao;

    @Override
    @Transactional
    public ReturnResponse examine(ReviewParam reviewParam) {
        if (reviewParam == null ){
            return ReturnResponse.failed("数据不存在!");
        }
        String desc = EnumUtil.getByCode(TabooOperateEnum.class, reviewParam.getOper()).getDesc();
        Long id = reviewParam.getId();
        String contentId = contentreviewDao.selectById(id);
        FootprintQuery footprintQuery = footprintDao.selById(Long.valueOf(contentId));
        if (footprintQuery ==null){
            String str=String.format("审核id为%d对应的商友圈id为%s的数据不存在",id,contentId, desc);
            return  ReturnResponse.failed(str);
        }
        int i = footprintDao.updateOneFootprint(Long.valueOf(contentId),reviewParam.getOper());
        if (i > 0) {
            updateOrInsert(id,contentId, reviewParam.getType());
            log.info("商友圈id为{}的数据审批{}成功",contentId,desc);
            String str=String.format("商友圈id为%s的数据审批%s成功",contentId, desc);
            insertAllcontentReviewLog(contentId,0, reviewParam.getOper(),2);
            return ReturnResponse.failed(str);
        }
        log.info("商友圈id为{}的数据审批{}失败",contentId,desc);
        String str=String.format("商友圈id为%s的数据审批%s失败",contentId, desc);
        return ReturnResponse.failed(str);
    }

    @Override
    public void handleContentreview(ContentReviewDTO contentReviewDTO,ContentReviewRep contentReviewRep) {
        //f.content,fp.photo_url
//        @ApiModelProperty(value = "商友圈footprint内容")
//        private String fcontent;
//        @ApiModelProperty(value = "商友圈照片链接")
//        private List<String> fphotoUrls;

        if (contentReviewDTO != null && contentReviewDTO.getContent() != null) {
            BeanUtils.copyProperties(contentReviewDTO, contentReviewRep);
            String s = String.valueOf(contentReviewDTO.getContent());
            List<String> imgUrls = new ArrayList();
            String content = null;
            Integer auditResult = null;
            if (StringUtils.isNoneEmpty(s)) {
                String[] split = s.split(";",-1);
                Arrays.stream(split).forEach(s1 -> imgUrls.add(PatternUtils.reg(s1)));
                content = split[0].split("https")[0];
                auditResult = Integer.valueOf(split[0].substring(split[0].length()-1));
            }
            contentReviewRep.setId(contentReviewDTO.getId());
            contentReviewRep.setFcontent(content);
            contentReviewRep.setFphotoUrls(imgUrls);
            contentReviewRep.setAuditResult(auditResult);
        }
    }

    @Override
    @Transactional
    public ReturnResponse handleExamine(Map<Integer,List<Long>> maps) {
        int oper = 0;
        List<Long> list = null;
        if (maps == null || maps.size() == 0){
            return ReturnResponse.success("商友圈无通过或屏蔽数据");
        }
        for(Map.Entry<Integer, List<Long>> vo : maps.entrySet()){
            oper = vo.getKey();
            list = vo.getValue();
        }
        String desc = EnumUtil.getByCode(TabooOperateEnum.class, oper).getDesc();
        AtomicInteger i = new AtomicInteger();
        for(Map.Entry<Integer, List<Long>> entry : maps.entrySet()){
            List<Long> ids = new ArrayList<>();
            for (Long id: entry.getValue()) {
                String contentId = contentreviewDao.selectById(id);
                FootprintQuery footprintQuery = footprintDao.selById(Long.valueOf(contentId));
                if (footprintQuery ==null){
                    String str=String.format("审核id为%d对应的商友圈id为%s的数据不存在",id,contentId, desc);
                    return  ReturnResponse.success(str);
                }
                ids.add(Long.valueOf(contentId));
            }
            int i1 = footprintDao.handleExamine(entry.getKey(),ids);
            i.set(i1);
        }
        if (i.get() == list.size()){
            log.info("商友圈批量{}成功",desc);
            String str=String.format("商友圈批量%s成功", desc);
            int finalOper = oper;
            maps.forEach((k, v)->{
                v.stream().forEach(id->{
                    String contentId = contentreviewDao.selectById(id);
                    updateOrInsert(id, contentId,1);
                    insertAllcontentReviewLog(contentId,0, finalOper,2);
                });
            });
            return ReturnResponse.success(str);
        }
        log.info("商友圈批量{}失败",desc);
        String str=String.format("商友圈批量%s失败", desc);
        return ReturnResponse.success(str);
    }


    @Override
    public void afterPropertiesSet() {
        HandlerContext.putHandler(ReviewEnum.FOOTPRINT.getCode(),this);
    }
}
