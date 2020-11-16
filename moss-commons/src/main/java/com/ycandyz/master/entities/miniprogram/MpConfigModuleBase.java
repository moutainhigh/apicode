package com.ycandyz.master.entities.miniprogram;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description  数据表字段映射类
 * </p>
 *
 * @author Wang Yang
 * @since 2020-11-15
 * @version 2.0
 */
@Getter
@Setter
@TableName("mp_config_module_base")
@ApiModel(description="")
public class MpConfigModuleBase extends Model {


   @ApiModelProperty(value = "编号")
   @TableId(value = "id", type = IdType.AUTO)
   private Integer id;

   @ApiModelProperty(value = "模块编号")
   private Integer moduleId;

   @ApiModelProperty(value = "元素名称")
   private String baseName;

   @ApiModelProperty(value = "元素编码")
   private String baseCode;

   @ApiModelProperty(value = "展示样式1->平铺，2->滑动；3->丰富；4->简化")
   private String showLayout;

   @ApiModelProperty(value = "更换图标地址")
   private String replacePicUrl;

   @ApiModelProperty(value = "展示数量限制")
   private Integer displayNum;


}
