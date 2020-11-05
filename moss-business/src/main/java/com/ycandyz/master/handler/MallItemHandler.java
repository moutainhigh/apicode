package com.ycandyz.master.handler;

import com.ycandyz.master.abstracts.AbstractHandler;
import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.dao.mall.goodsManage.MallItemDao;
import com.ycandyz.master.domain.query.risk.ReviewParam;
import com.ycandyz.master.domain.response.risk.ContentReviewRep;
import com.ycandyz.master.dto.risk.ContentReviewDTO;
import com.ycandyz.master.enums.ReviewEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class MallItemHandler extends AbstractHandler {


    @Autowired
    private MallItemDao mallItemDao;

    @Override
    public ReturnResponse handle(ReviewParam reviewParam) {
        int i = mallItemDao.updateOneMallItem(String.valueOf(reviewParam.getContentId()),reviewParam.getOper());
        if (i > 0) {
            updateOrInsert(reviewParam.getContentId(), reviewParam.getType());
            ReturnResponse.success("更新成功");
        }
        return ReturnResponse.failed("更新失败");
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

    @Override
    public void afterPropertiesSet() {
        HandlerContext.putHandler(ReviewEnum.MALLITEM.getCode(),this);
    }
}
