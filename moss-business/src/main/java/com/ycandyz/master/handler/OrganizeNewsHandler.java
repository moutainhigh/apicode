package com.ycandyz.master.handler;

import com.ycandyz.master.abstracts.AbstractHandler;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.dao.organize.OrganizeNewsDao;
import com.ycandyz.master.domain.query.risk.ReviewParam;
import com.ycandyz.master.domain.response.risk.ContentReviewRep;
import com.ycandyz.master.dto.risk.ContentReviewDTO;
import com.ycandyz.master.enums.ReviewEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrganizeNewsHandler extends AbstractHandler {

    @Autowired
    private OrganizeNewsDao organizeNewsDao;

    @Override
    public ReturnResponse handle(ReviewParam reviewParam) {
        int i = organizeNewsDao.updateOneOrganizeNews(reviewParam.getContentId(),reviewParam.getOper());
        if (i > 0) {
            updateOrInsert(reviewParam.getContentId(), reviewParam.getType());
            return ReturnResponse.success("更新成功");
        }
        return ReturnResponse.failed("更新失败");
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
            String content = String.valueOf(contentReviewDTO.getContent());
            if (content != null){
                String[] split = content.split(";",-1);
                if (content.startsWith(";")){
                    contentReviewRep.setOabstracts(null);
                    contentReviewRep.setOtitle(split[0]);
                    contentReviewRep.setOndContent(split[3]);
                    String[] urls = new String[2];
                    urls[0] = split[1];
                    urls[1] = split[2];
                    contentReviewRep.setOImgUrls(urls);
                }else {
                    contentReviewRep.setOabstracts(split[0]);
                    contentReviewRep.setOtitle(split[1]);
                    contentReviewRep.setOndContent(split[4]);
                    String[] urls = new String[2];
                    urls[0] = split[2];
                    urls[1] = split[3];
                    contentReviewRep.setOImgUrls(urls);
                }
            }
        }

    }

    @Override
    public void afterPropertiesSet() {
        HandlerContext.putHandler(ReviewEnum.ORGANIZENEWS.getCode(), this);
    }
}
