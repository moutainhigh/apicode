package com.ycandyz.master.domain.query.userExportRecord;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * <p>
 * @Description  数据表字段映射类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-04
 * @version 2.0
 */
@Getter
@Setter
@TableName("user_export_record")
@ApiModel(description="")
public class UserExportRecordReq extends Model {


   @ApiModelProperty(value = "操作终端:1-U客企业后台 2-有传运营后台")
   private Integer terminal;

   @ApiModelProperty(value = "企业编号")
   private Long organizeId;

   @ApiModelProperty(value = "操作人id")
   private Long operatorId;

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

}
