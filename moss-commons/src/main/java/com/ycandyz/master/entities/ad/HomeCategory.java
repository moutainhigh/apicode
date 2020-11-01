package com.ycandyz.master.entities.ad;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ycandyz.master.validation.ValidatorContract;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * <p>
 * @Description 首页- 轮播图分类表 数据表字段映射类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-15
 * @version 2.0
 */
@Getter
@Setter
@TableName("ad_home_category")
@ApiModel(description="首页- 轮播图分类表")
public class HomeCategory extends Model {


   @ApiModelProperty(value = "ID")
   @TableId(value = "id", type = IdType.AUTO)
   private Long id;

   @ApiModelProperty(value = "商店编号")
   private String shopNo;

   @ApiModelProperty(value = "分类编号")
   @Size(min = 1, max = 64, message = "分类编号长度要求1到64之间。")
   @NotNull(message = "分类编号不能为空",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
   private String categoryNo;

   @Size(min = 1, max = 64, message = "分类编号长度要求1到64之间。")
   @ApiModelProperty(value = "父分类编号")
   private String parentCategoryNo;

   @ApiModelProperty(value = "分类名称")
   @Size(min = 1, max = 512, message = "分类名称长度要求1到512之间。",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
   @NotNull(message = "分类名称不能为空",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
   private String categoryName;

   @ApiModelProperty(value = "分类图片")
   @Size(min = 8, max = 512, message = "分类图片地址长度要求8到512之间。",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
   @NotNull(message = "分类图片地址不能为空",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
   private String categoryImg;

   @ApiModelProperty(value = "分类处于第几级")
   private Integer layer;

   @ApiModelProperty(value = "排序值")
   private Integer sortValue;

   @Min(value = 0, message = "最小值为0")
   @Max(value = 1, message = "最大值为1")
   @ApiModelProperty(value = "状态：1: 正常，0: 无效  默认1")
   private Integer status;

   @ApiModelProperty(value = "分类类型:0-页面,1分类")
   private Integer type;

   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
   @ApiModelProperty(value = "创建时间")
   private Date createdTime;

   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
   @ApiModelProperty(value = "更新时间")
   private Date updatedTime;


}
