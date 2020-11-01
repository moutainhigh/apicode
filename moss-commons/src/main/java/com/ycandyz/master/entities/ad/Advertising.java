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
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * <p>
 * @Description 轮播图 数据表字段映射类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-13
 * @version 2.0
 */
@Getter
@Setter
@TableName("ad_advertising")
@ApiModel(description="首页-baseMapper")
public class Advertising extends Model {


   @ApiModelProperty(value = "ID")
   @TableId(value = "id", type = IdType.AUTO)
   private Long id;

   @ApiModelProperty(value = "标题")
   @Size(min = 1, max = 100, message = "标题长度要求1到100之间。",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
   @NotNull(message = "标题不能为空",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
   private String title;

   @ApiModelProperty(value = "图片地址")
   @Size(min = 5, max = 1000, message = "图片地址长度要求5到1000之间。",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
   @NotNull(message = "图片地址不能为空",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
   private String img;

   @Max(value = 999, message = "排序值不能大于999",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
   @ApiModelProperty(value = "排序值")
   private Integer sort;

   @ApiModelProperty(value = "类别(0:商品,1页面)")
   @Range(min = 0, max = 1, message = "类别范围为0至1",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
   @NotNull(message = "类别不能为空",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
   private Integer type;

   @ApiModelProperty(value = "商品编号")
   private String itemNo;

   @ApiModelProperty(value = "商店编号")
   @NotNull(message = "商店编号不能为空",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
   private String shopNo;

   @ApiModelProperty(value = "分类编号")
   private String categoryNo;

   @ApiModelProperty(value = "跳转地址")
   private String address;

   @ApiModelProperty(value = "启用标识(0启用,1禁用)")
   @Range(min = 0, max = 1, message = "启用标识范围为0至1",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
   @NotNull(message = "是否展示不能为空",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
   private Integer enabled;

   @ApiModelProperty(value = "删除标识")
   @Range(min = 0, max = 1, message = "删除标识范围为0至1",groups = {ValidatorContract.OnUpdate.class, ValidatorContract.OnCreate.class})
   private Integer deleted;

   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
   @ApiModelProperty(value = "创建时间")
   private Date createdTime;

   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
   @ApiModelProperty(value = "更新时间")
   private Date updatedTime;


}
