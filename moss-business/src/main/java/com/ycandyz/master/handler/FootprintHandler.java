package com.ycandyz.master.handler;

import com.ycandyz.master.abstracts.AbstractHandler;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.dao.footprint.FootprintDao;
import com.ycandyz.master.domain.query.risk.ReviewParam;
import com.ycandyz.master.domain.response.risk.ContentReviewRep;
import com.ycandyz.master.dto.risk.ContentReviewDTO;
import com.ycandyz.master.enums.ReviewEnum;
import com.ycandyz.master.enums.TabooOperateEnum;
import com.ycandyz.master.utils.EnumUtil;
import com.ycandyz.master.utils.PatternUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    @Override
    @Transactional
    public ReturnResponse examine(ReviewParam reviewParam) {
        if (reviewParam == null ){
            return ReturnResponse.success("商友圈更新数据为null");
        }
        String desc = EnumUtil.getByCode(TabooOperateEnum.class, reviewParam.getOper()).getDesc();
        String id = reviewParam.getContentId();
        int i = footprintDao.updateOneFootprint(Long.valueOf(id),reviewParam.getOper());
        if (i > 0) {
            updateOrInsert(id, reviewParam.getType());
            log.info("商友圈id为{}的数据审批{}成功",id,desc);
            String str=String.format("商友圈id为%s的数据审批%s成功",id, desc);
            insertAllcontentReviewLog(id,0, reviewParam.getOper(),2);
            return ReturnResponse.success(str);
        }
        log.info("商友圈id为{}的数据审批{}失败",id,desc);
        String str=String.format("商友圈id为%s的数据审批%s失败",id, desc);
        return ReturnResponse.success(str);
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
            contentReviewRep.setId(contentReviewDTO.getContentId());
            contentReviewRep.setFcontent(content);
            contentReviewRep.setFphotoUrls(imgUrls);
            contentReviewRep.setAuditResult(auditResult);
        }
    }

    @Override
    @Transactional
    public ReturnResponse handleExamine(Map<Integer,List<String>> maps) {
        int oper = 0;
        List<String> list = null;
        if (maps == null || maps.size() == 0){
            return ReturnResponse.success("商友圈无通过或屏蔽数据");
        }
        for(Map.Entry<Integer, List<String>> vo : maps.entrySet()){
            oper = vo.getKey();
            list = vo.getValue();
        }
        String desc = EnumUtil.getByCode(TabooOperateEnum.class, oper).getDesc();
        AtomicInteger i = new AtomicInteger();
        maps.forEach((k,v)->{
            int i1 = footprintDao.handleExamine(k, v);
            i.set(i1);
        });
        if (i.get() == list.size()){
            log.info("商友圈批量{}成功",desc);
            String str=String.format("商友圈批量%s成功", desc);
            int finalOper = oper;
            maps.forEach((k, v)->{
                v.stream().forEach(id -> updateOrInsert(id, 1));
                v.stream().forEach(id2-> insertAllcontentReviewLog(id2,0, finalOper,2));
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
