package com.ycandyz.master.dto.msg;

import com.ycandyz.master.entities.msg.MsgNotifyRelation;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 业务通知关系表 实体类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-13
 * @version 2.0
 */
@Getter
@Setter
@TableName("msg_notify_relation")
public class MsgNotifyRelationDTO extends MsgNotifyRelation {

}
