package com.ycandyz.master.domain.response.risk;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ContentReviewRep {

    @ApiModelProperty(value = "商品详情itemNo;商友圈id;企业动态id")
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
    private String[] oImgUrls;

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

    @ApiModelProperty(value = "审核时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date auditTime;

    @ApiModelProperty(value = "审核结果 [商品详情is_del 系统屏蔽 0-通过 2-屏蔽;企业动态is_del 是否删除 0-正常 2-系统屏蔽；商友圈 is_del 是否删除 0-否  2-系统屏蔽]")
    private Integer auditResult;

}
