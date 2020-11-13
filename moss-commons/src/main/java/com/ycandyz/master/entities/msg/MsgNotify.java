package com.ycandyz.master.entities.msg;

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
 * @Description 业务通知表 数据表字段映射类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-13
 * @version 2.0
 */
@Getter
@Setter
@TableName("msg_notify")
@ApiModel(description="业务通知表")
public class MsgNotify extends Model {


   @ApiModelProperty(value = "业务通知表主键")
   @TableId(value = "id", type = IdType.AUTO)
   private Integer id;

   @ApiModelProperty(value = "标题")
   private String title;

   @ApiModelProperty(value = "内容，存在时弹窗提示")
   private String content;

   @ApiModelProperty(value = "链接，外部链接或内部相对路径，存在时为页面跳转")
   private String url;

   @ApiModelProperty(value = "功能操作类型：1 查看，2 重新提交，3 去认证，4 去缴费")
   private Integer actionType;

   @ApiModelProperty(value = "创建时间")
   private Long createdAt;


}
