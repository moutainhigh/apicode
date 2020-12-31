package com.ycandyz.master.entities.leafletTemplate;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * <p>
 *
 * @author WenXin
 * @version 2.0
 * @Description 模板内容表 数据表字段映射类
 * </p>
 * @since 2020-12-18
 */
@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@TableName("template_original")
@ApiModel(description = "模板内容表")
public class TemplateOriginal extends Model {

    @ApiModelProperty(name = "id", value = "默认模板id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(name = "template_name", value = "模板名称")
    private String templateName;

    @ApiModelProperty(name = "share_title", value = "分享标题")
    private String shareTitle;

    @ApiModelProperty(name = "share_desc", value = "分享描述")
    private String shareDesc;

    @ApiModelProperty(name = "share_img", value = "分享配图")
    private String shareImg;

    @ApiModelProperty(name = "template_img", value = "模板配图")
    private String templateImg;

    @ApiModelProperty(name = "user_id", value = "创建人")
    private Long userId;

    @ApiModelProperty(name = "organize_id", value = "企业编号")
    private String organizeId;

    @ApiModelProperty(name = "components", value = "模板组件")
    private String components;

    @ApiModelProperty(name = "classify_name", value = "类别名称")
    private String classifyName;

    @ApiModelProperty(name = "created_time", value = "创建时间")
    private Date createdTime;

    @ApiModelProperty(name = "updated_time", value = "更新时间")
    private Date updatedTime;


}
