package com.ycandyz.master.domain.query.userExportRecord;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ycandyz.master.entities.userExportRecord.UserExportRecord;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * <p>
 * @Description  Resp
 * </p>
 *
 * @author SanGang
 * @since 2020-11-04
 * @version 2.0
 */
@Getter
@Setter
@TableName("user_export_record")
public class UserExportRecordVo{

    @ApiModelProperty(value = "操作终端:1-U客企业后台 2-有传运营后台")
    private String terminal;

    @ApiModelProperty(value = "企业名称")
    private String organizeName;

    @ApiModelProperty(value = "操作人姓名")
    private String operatorName;

    @ApiModelProperty(value = "ip地址")
    private String operatorIp;

    @ApiModelProperty(value = "操作容的系统")
    private String opertorSystem;

    @ApiModelProperty(value = "操作人的浏览器")
    private String opertorBrowser;

    @ApiModelProperty(value = "导出文件名称")
    private String exportFileName;

    @ApiModelProperty(value = "导出文件链接")
    private String exportFileUrl;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "导出时间")
    private Date createdTime;
}
