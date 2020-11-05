package com.ycandyz.master.dto.risk;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ContentDTO {

    //m.item_name,m.item_text,m.banners,m.share_descr,m.share_img
    @ApiModelProperty(value = "商品名称")
    private String itemName;

    @ApiModelProperty(value = "商品描述")
    private String itemText;

    @ApiModelProperty(value = "轮播图，jsonarray")
    private String banners;

    @ApiModelProperty(value = "分享描述")
    private String shareDescr;

    @ApiModelProperty(value = "分享图片")
    private String shareImg;

    //f.f_content,fp.photo_url
    @ApiModelProperty(value = "footprint内容")
    private String fContent;

    @ApiModelProperty(value = "照片链接")
    private String photoUrl;

    //o.abstract,o.title,o.cover,o.link_url,ond.ond_content
    @ApiModelProperty(value = "摘要")
    private String abstracts;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "封面")
    private String cover;

    @ApiModelProperty(value = "链接")
    private String linkUrl;

    @ApiModelProperty(value = "organize_news_detail内容")
    private String ondContent;

}
