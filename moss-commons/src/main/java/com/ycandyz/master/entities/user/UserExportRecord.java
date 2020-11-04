package com.ycandyz.master.entities.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@TableName("user_export_record")
@ApiModel(description="用户导出记录表")
public class UserExportRecord extends Model {

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "操作终端:1-U客企业后台 2-有传运营后台")
    private Integer terminal;

    @ApiModelProperty(value = "企业编号")
    private Long organizeId;

    @ApiModelProperty(value = "企业名称")
    private String organizeName;

    @ApiModelProperty(value = "操作人id")
    private Long operatorId;

    @ApiModelProperty(value = "操作人姓名")
    private String operatorName;

    @ApiModelProperty(value = "操作人手机号")
    private String operatorPhone;

    @ApiModelProperty(value = "ip地址")
    private String operatorIp;

    @ApiModelProperty(value = "操作的系统")
    private String opertorSystem;

    @ApiModelProperty(value = "操作人的浏览器")
    private String opertorBrowser;

    @ApiModelProperty(value = "导出文件名称")
    private String exportFileName;

    @ApiModelProperty(value = "导出文件链接")
    private String exportFileUrl;

    @ApiModelProperty(value = "导出时间")
    private Long created_at;

    @ApiModelProperty(value = "导出时间")
    private Date createdTime;

}
