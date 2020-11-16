package com.ycandyz.master.entities.miniprogram;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * <p>
 * @Description 小程序转交接申请 数据表字段映射类
 * </p>
 *
 * @author Wang Yang
 * @since 2020-11-16
 * @version 2.0
 */
@Getter
@Setter
@ApiModel(description="企业小程序修改")
public class MpOrganizeStyleMoudle extends Model {


   @ApiModelProperty(value = "编号")
   private Integer id;

   @ApiModelProperty(value = "元素名称")
   private String baseName;



}
