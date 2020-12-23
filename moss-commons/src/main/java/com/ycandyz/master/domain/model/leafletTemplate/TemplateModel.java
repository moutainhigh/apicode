package com.ycandyz.master.domain.model.leafletTemplate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ycandyz.master.validation.ValidatorContract;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * @author WenXin
 * @version 2.0
 * @Description 模板定义表 Model
 * </p>
 * @since 2020-12-18
 */
@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description = "模板定义表-Model")
public class TemplateModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "id", value = "模板id")
    private Long id;

    @Size(max = 255, message = "模板名称长度不能大于255。", groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    @NotNull(message = "模板名称不能为空", groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    @ApiModelProperty(name = "template_name", value = "模板名称")
    private String templateName;

    @Size(max = 255, message = "分享标题长度不能大于255。", groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    @NotNull(message = "分享标题不能为空", groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    @ApiModelProperty(name = "share_title", value = "分享标题")
    private String shareTitle;

    @Size(max = 255, message = "分享描述长度不能大于255。", groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    @ApiModelProperty(name = "share_desc", value = "分享描述")
    private String shareDesc;

    @ApiModelProperty(name = "share_img", value = "分享配图")
    private String shareImg;

    @ApiModelProperty(name = "template_img", value = "模板配图")
    private String templateImg;

    @ApiModelProperty(name = "submit_restriction", value = "提交次数限制(默认为1次)")
    private Integer submitRestriction;

    @ApiModelProperty(name = "template_status", value = "模板状态（0:正常，1:删除）")
    private Boolean templateStatus;

    @ApiModelProperty(name = "user_id", value = "创建人")
    private Long userId;

    @ApiModelProperty(name = "organize_id", value = "企业编号")
    private Integer organizeId;

    @ApiModelProperty(name = "created_time", value = "创建时间")
    private Date createdTime;

    @ApiModelProperty(name = "updated_time", value = "更新时间")
    private Date updatedTime;

    @NotNull(message = "失效时间不能为空", groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    @ApiModelProperty(name = "end_time", value = "失效时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    @ApiModelProperty(name = "components", value = "模板明细")
    private List<TemplateDetailModel> components;

    @ApiModelProperty(name = "classify_type", value = "模板类别")
    private Integer classifyType;

}

