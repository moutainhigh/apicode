package com.ycandyz.master.domain.query.mall;

import com.ycandyz.master.annotation.Condition;
import com.ycandyz.master.enums.ConditionEnum;

import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * @Description  检索参数类
 * </p>
 *
 * @author Wang Yang
 * @since 2020-10-13
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="-检索参数")
public class MallSelfPickUpAddressQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @Condition(field = "id", condition = ConditionEnum.EQ)
    private Integer id;

    @ApiModelProperty(value = "地址名称")
    private String name;

    @ApiModelProperty(value = "省份信息")
    private String province;

    @ApiModelProperty(value = "省份id")
    private Integer provinceId;

    @ApiModelProperty(value = "城市信息")
    private String city;

    @ApiModelProperty(value = "城市id")
    private Integer cityId;

    @ApiModelProperty(value = "地区信息")
    private String district;

    @ApiModelProperty(value = "地区id")
    private Integer districtId;

    @ApiModelProperty(value = "店铺编号")
    private String shopNo;

    @ApiModelProperty(value = "是否删除 0-否 1-删除")
    private Boolean isDel;

    @ApiModelProperty(value = "详细地址信息")
    private String detailInfo;

    @ApiModelProperty(value = "是否默认 0-非默认 1-默认")
    private Boolean isDefault;

    @ApiModelProperty(value = "创建时间起")
    @Condition(field = "created_time", condition = ConditionEnum.GE)
    private Date createdTimeS;
    
    @ApiModelProperty(value = "创建时间止")
    @Condition(field = "created_time", condition = ConditionEnum.LE)
    private Date createdTimeE;

    @ApiModelProperty(value = "修改时间起")
    @Condition(field = "updated_time", condition = ConditionEnum.GE)
    private Date updatedTimeS;
    
    @ApiModelProperty(value = "修改时间止")
    @Condition(field = "updated_time", condition = ConditionEnum.LE)
    private Date updatedTimeE;

    private Long createdAt;

    private Long updatedAt;



}
