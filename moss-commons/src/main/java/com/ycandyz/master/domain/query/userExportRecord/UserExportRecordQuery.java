package com.ycandyz.master.domain.query.userExportRecord;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * @Description  检索参数类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-04
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="-检索参数")
public class UserExportRecordQuery implements Serializable {


    @ApiModelProperty(value = "操作终端:1-U客企业后台 2-有传运营后台")
    private Integer terminal;

    @ApiModelProperty(value = "企业名称")
    private String organizeName;

    @ApiModelProperty(value = "操作人id")
    private Long operatorId;

    @ApiModelProperty(value = "导出时间起")
    private Long createdAtStart;

    @ApiModelProperty(value = "导出时间止")
    private Long createdAtEnd;


}
