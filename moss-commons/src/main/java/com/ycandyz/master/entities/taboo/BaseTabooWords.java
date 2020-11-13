package com.ycandyz.master.entities.taboo;

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
 * @Description 基础商品表 数据表字段映射类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-23
 * @version 2.0
 */
@Getter
@Setter
@TableName("base_taboo_words")
@ApiModel(value="base_taboo_words对象", description="敏感词表")
public class BaseTabooWords extends Model {

   @ApiModelProperty(value = "新增修改同一个topic,区分新增和修改入库[1新增 2修改 3删除]")
   private Integer flag;

   @ApiModelProperty(value = "主键")
   @TableId(value = "id", type = IdType.AUTO)
   private Long id;

   @ApiModelProperty(value = "词组名称")
   private String phraseName;

   @ApiModelProperty(value = "敏感词")
   private String tabooWords;

   @ApiModelProperty(value = "处理方式")
   private Integer treatmentMethod;

   @ApiModelProperty(value = "操作人员")
   private Long operator;

   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
   @ApiModelProperty(value = "创建时间")
   private Date createdTime;

   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
   @ApiModelProperty(value = "更新时间")
   private Date updatedTime;


}
