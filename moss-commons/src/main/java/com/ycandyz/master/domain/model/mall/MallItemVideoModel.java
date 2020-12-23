package com.ycandyz.master.domain.model.mall;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ycandyz.master.validation.ValidatorContract;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * <p>
 * @Description 商品视频信息 检索参数类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-10
 * @version 2.0
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="商品视频信息-参数")
public class MallItemVideoModel implements Serializable {

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "商品视频编号")
    private String videoNo;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "商品编号")
    @Size(max = 64, message = "商品编号长度不能大于64。",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    private String itemNo;

    @ApiModelProperty(value = "视频栏目(0置顶视频,1描述视频)")
    @NotNull(message = "视频栏目不能为空",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    @Range(min = 0, max = 1, message = "视频栏目类型不正确",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
    private Integer type;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "视频播放地址")
    private String url;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "视频缩略图")
    private String img;

}
