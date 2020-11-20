package com.ycandyz.master.domain.response.risk;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ycandyz.master.entities.mall.MallItemVideo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ContentReviewRep {

    @ApiModelProperty(value = "content_review主键id")
    private Long id;

//    @ApiModelProperty(value = "商品详情id")
//    private Long itemNo;

    @ApiModelProperty(value = "商品名称")
    private String itemName;

    @ApiModelProperty(value = "商品详情")
    private String itemText;

    @ApiModelProperty(value = "商品分享描述")
    private String itemShareDescr;

    @ApiModelProperty(value = "商品轮播图banners，商品分享图片shareImg")
    private String[] itemImgUrls;

    //f.f_content,fp.photo_url
//    @ApiModelProperty(value = "商友圈id")
//    private Long fId;

    @ApiModelProperty(value = "商友圈footprint内容")
    private String fcontent;

    @ApiModelProperty(value = "商友圈照片链接")
    private List<String> fphotoUrls;

    //o.abstract,o.title,o.cover,o.link_url,ond.ond_content
//    @ApiModelProperty(value = "企业动态id")
//    private Long oId;

    @ApiModelProperty(value = "企业动态摘要")
    private String oabstracts;

    @ApiModelProperty(value = "企业动态标题")
    private String otitle;

    @ApiModelProperty(value = "企业动态封面 cover;企业动态链接 link_url")
    private List<String> oImgUrls;

    @ApiModelProperty(value = "企业动态organize_news_detail内容")
    private String ondContent;

    @ApiModelProperty(value = "内容模块 [0:商品详情(表:mall_item);1:商友圈(表:footprint);2:企业动态(表:organize_news)]")
    private Integer type;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    @ApiModelProperty(value = "审核人")
    private Long auditor;

    @ApiModelProperty(value = "审核人姓名")
    private String auditorName;

    @ApiModelProperty(value = "审核时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date auditTime;

    @ApiModelProperty(value = "审核结果 [商品详情is_screen 系统屏蔽 0-通过 1-屏蔽;企业动态is_screen 是否删除 0-正常 1-系统屏蔽；商友圈is_screen 是否删除 0-否  1-系统屏蔽]")
    private Integer auditResult;

    @ApiModelProperty(value = "视频")
    private List<MallItemVideo> video;

}
