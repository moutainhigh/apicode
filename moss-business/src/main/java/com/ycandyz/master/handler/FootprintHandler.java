package com.ycandyz.master.handler;

import com.ycandyz.master.abstracts.AbstractHandler;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.dao.footprint.FootprintDao;
import com.ycandyz.master.domain.query.risk.ReviewParam;
import com.ycandyz.master.domain.response.risk.ContentReviewRep;
import com.ycandyz.master.dto.risk.ContentReviewDTO;
import com.ycandyz.master.enums.ReviewEnum;
import com.ycandyz.master.utils.PatternUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
public class FootprintHandler extends AbstractHandler {

    @Autowired
    private FootprintDao footprintDao;

    @Override
    public ReturnResponse handle(ReviewParam reviewParam) {

        int i = footprintDao.updateOneFootprint(reviewParam.getContentId(),reviewParam.getOper());
        if (i > 0) {
            updateOrInsert(reviewParam.getContentId(), reviewParam.getType());
            return ReturnResponse.success("更新成功");
        }
        return ReturnResponse.failed("更新失败");
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
            if (StringUtils.isNoneEmpty(s)) {
                String[] split = s.split(";",-1);
                Arrays.stream(split).forEach(s1 -> imgUrls.add(PatternUtils.reg(s1)));
                content = split[0].split("https")[0];
            }
            contentReviewRep.setFcontent(content);
            contentReviewRep.setFphotoUrls(imgUrls);
        }
    }


    @Override
    public void afterPropertiesSet() {
        HandlerContext.putHandler(ReviewEnum.FOOTPRINT.getCode(),this);
    }
}
