package com.ycandyz.master.entities.miniprogram;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.annotations.InsertProvider;

/**
 * <p>
 * @Description 小程序配置方案 数据表字段映射类
 * </p>
 *
 * @author WangYang
 * @since 2020-11-13
 * @version 2.0
 */
@Getter
@Setter
@TableName("mp_config_plan")
@ApiModel(description="小程序配置方案")
public class MpConfigPlan extends Model {


   @ApiModelProperty(value = "编号")
   @TableId(value = "id", type = IdType.AUTO)
   private Integer id;

   @ApiModelProperty(value = "方案名称")
   private String planName;

   @ApiModelProperty(value = "是否同步U客")
   private Boolean syncUke;

   @ApiModelProperty(value = "企业选择数量")
   private Integer organizeChooseNum;

   @ApiModelProperty(value = "方案展示图")
   private String stylePicUrl;

   @ApiModelProperty(value = "创建时间")
   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
   @TableField(value = "create_time", fill = FieldFill.INSERT)
   private Date createTime;

   @ApiModelProperty(value = "修改时间")
   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
   @TableField(value = "update_time", fill = FieldFill.INSERT)
   private Date updateTime;


}
