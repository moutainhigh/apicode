package com.ycandyz.master.entities.organize;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 集团表 数据表字段映射类
 * @author SanGang
 * @since 2020-10-23
 * @version 2.0
 */

@TableName("organize_group")
@ApiModel(description="集团表")
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrganizeGroup extends Model {


   @TableId(value = "id", type = IdType.AUTO)
   private Long id;

   private Integer organizeId;

   @ApiModelProperty(value = "集团名字")
   private String groupName;

   @ApiModelProperty(value = "创建日期")
   private Date createdAt;

   @ApiModelProperty(value = "创建人id")
   private Integer creater;

   @ApiModelProperty(value = "锁客状态 0 关闭 1开启")
   private Boolean lockStatus;

   @ApiModelProperty(name="is_open_maintainable",value = "门店是否需可维护 0关闭 1开启")
   private Boolean isOpenMaintainable;


}
