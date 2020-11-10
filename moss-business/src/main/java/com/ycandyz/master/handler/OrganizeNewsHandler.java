package com.ycandyz.master.handler;

import com.ycandyz.master.abstracts.AbstractHandler;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.dao.organize.OrganizeNewsDao;
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

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class OrganizeNewsHandler extends AbstractHandler {

    @Autowired
    private OrganizeNewsDao organizeNewsDao;

    @Override
    @Transactional
    public ReturnResponse examine(ReviewParam reviewParam) {
        if (reviewParam == null ){
            return ReturnResponse.success("企业动态更新数据为null");
        }
        String desc = EnumUtil.getByCode(TabooOperateEnum.class, reviewParam.getOper()).getDesc();
        Long id = reviewParam.getContentId();
        int i = organizeNewsDao.updateOneOrganizeNews(reviewParam.getContentId(),reviewParam.getOper());
        if (i > 0) {
            updateOrInsert(reviewParam.getContentId(), reviewParam.getType());
            log.info("企业动态id为{}的数据审批{}成功",id,desc);
            String str=String.format("企业动态id为%d的数据审批%s成功",id, desc);
            return ReturnResponse.success(str);
        }
        log.info("企业动态id为{}的数据审批{}成功",id,desc);
        String str=String.format("企业动态id为%d的数据审批%s成功",id, desc);
        return ReturnResponse.success(str);
    }

    @Override
    public void handleContentreview(ContentReviewDTO contentReviewDTO, ContentReviewRep contentReviewRep) {
        //o.abstract,o.title,o.cover,o.link_url,ond.content
//        @ApiModelProperty(value = "企业动态摘要")
//        private String oabstracts;
//        @ApiModelProperty(value = "企业动态标题")
//        private String otitle;
//        @ApiModelProperty(value = "企业动态封面 cover;企业动态链接 link_url")
//        private String[] oImgUrls;
//        @ApiModelProperty(value = "企业动态organize_news_detail内容")
//        private String ondContent;
        if (contentReviewDTO != null && contentReviewDTO.getContent() != null){
            BeanUtils.copyProperties(contentReviewDTO,contentReviewRep);
            contentReviewRep.setId(contentReviewDTO.getContentId());
            String content = String.valueOf(contentReviewDTO.getContent());
            if (content != null){
                String[] split = content.split("︵",-1);
                contentReviewRep.setOabstracts(split[0]);
                contentReviewRep.setOtitle(split[1]);
                contentReviewRep.setOndContent(split[5]);
                String[] urls = new String[2];
                urls[0] = split[2];
                urls[1] = split[3];
                contentReviewRep.setOImgUrls(urls);
                contentReviewRep.setAuditResult(Integer.valueOf(split[4]));
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
            return ReturnResponse.success("企业动态无通过或屏蔽数据");
        }
        for(Map.Entry<Integer, List<Long>> vo : maps.entrySet()){
            oper = vo.getKey();
            list = vo.getValue();
        }
        String desc = EnumUtil.getByCode(TabooOperateEnum.class, oper).getDesc();
        AtomicInteger i = new AtomicInteger();
        maps.forEach((k,v)->{
            int i1 = organizeNewsDao.handleExamine(k, v);
            v.stream().forEach(id -> updateOrInsert(id, 2));
            i.set(i1);
        });
        if (i.get() == list.size()){
            String str=String.format("企业动态批量%s成功", desc);
            return ReturnResponse.success(str);
        }
        String str=String.format("企业动态批量%s成功", desc);
        return ReturnResponse.success(str);
    }

    @Override
    public void afterPropertiesSet() {
        HandlerContext.putHandler(ReviewEnum.ORGANIZENEWS.getCode(), this);
    }
}
