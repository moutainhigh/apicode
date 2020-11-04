package com.ycandyz.master.entities.organize;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 企业动态 数据表字段映射类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-27
 * @version 2.0
 */
@Getter
@Setter
@TableName("organize_news")
@ApiModel(description="企业动态")
public class OrganizeNews extends Model {


   @TableId(value = "id", type = IdType.AUTO)
   private Long id;

   @ApiModelProperty(value = "组织id")
   private Long organizeId;

   @ApiModelProperty(value = "动态类型 1-图文动态 2-外链动态")
   private Boolean type;

   @ApiModelProperty(value = "标题")
   private String title;

   @ApiModelProperty(value = "摘要")
   private String abstracts;

   @ApiModelProperty(value = "封面图")
   private String cover;

   @ApiModelProperty(value = "链接地址")
   private String linkUrl;

   @ApiModelProperty(value = "点赞数")
   private Integer likeNum;

   @ApiModelProperty(value = "浏览数")
   private Integer viewNum;

   @ApiModelProperty(value = "状态：0-未发布 1-已发布 2-已禁用 3-系统禁用")
   private Boolean status;

   @ApiModelProperty(value = "分享数")
   private Integer shareNum;

   @ApiModelProperty(value = "发布时间")
   private Integer publishedAt;

   @ApiModelProperty(value = "禁用时间")
   private Integer disabledAt;

   @ApiModelProperty(value = "禁用原因")
   private String disableReason;

   @ApiModelProperty(value = "是否删除 0-正常 1-已删除")
   private Boolean isDel;

   @ApiModelProperty(value = "删除原因")
   private String delReason;

   @ApiModelProperty(value = "删除时间")
   private Integer deletedAt;

   @ApiModelProperty(value = "创建时间")
   private Integer createdAt;

   @ApiModelProperty(value = "更新时间")
   private Integer updatedAt;

   @ApiModelProperty(value = "分享者id")
   private Long originUserId;


}
