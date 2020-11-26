package com.ycandyz.master.domain.query.risk;

import com.ycandyz.master.validation.ValidatorContract;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TabooCheckParams {


    @ApiModelProperty(value = "审核模块id",required = true)
    @NotNull(message = "审核模块id(contentId)不能为空",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    private String contentId;

    @ApiModelProperty(value = "企业动态标题")
    private String title;

    @ApiModelProperty(value = "企业动态摘要")
    private String abstracts;

    @ApiModelProperty(value = "商友圈内容")
    private String content;

    @ApiModelProperty(value = "商品名称")
    private String itemName;

    @ApiModelProperty(value = "商品描述")
    private String itemText;

    @ApiModelProperty(value = "商品分享描述")
    private String shareDescr;

    @ApiModelProperty(value = "内容模块[0:商品详情(表:mall_item);1:商友圈(表:footprint);2:企业动态(表:organize_news)]",required = true)
    @NotNull(message = "内容模块[0:商品详情;1:商友圈;2:企业动态]不能为空",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    private Integer type;

}
