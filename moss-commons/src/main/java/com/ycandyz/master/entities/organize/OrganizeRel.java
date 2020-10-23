package com.ycandyz.master.entities.organize;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
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
 * @author SanGang
 * @since 2020-10-23
 * @version 2.0
 */
@Getter
@Setter
@TableName("organize_rel")
@ApiModel(description="")
public class OrganizeRel extends Model {


   @TableId(value = "id", type = IdType.AUTO)
   private Long id;

   private Date createdAt;

   private Date deletedAt;

   private Integer userId;

   private Integer userId1;

   @ApiModelProperty(value = "集团企业id")
   private Integer groupOrganizeId;

   @ApiModelProperty(value = "子企业id")
   private Integer organizeId;

   @ApiModelProperty(value = "1待授权 2已授权.3已拒绝 4 已解除 5已取消")
   private Integer status;

   @ApiModelProperty(value = "授权日期 关联日期")
   private Date relAt;

   @ApiModelProperty(value = "解除授权日期")
   private Date unrelAt;

   private Date updatedAt;

   private Integer relType;


}
