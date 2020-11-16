package com.ycandyz.master.domain.response.msg;

import com.ycandyz.master.entities.msg.MsgNotifyRelation;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 业务通知关系表 Resp
 * </p>
 *
 * @author SanGang
 * @since 2020-11-13
 * @version 2.0
 */
@Getter
@Setter
@TableName("msg_notify_relation")
public class MsgNotifyRelationResp extends MsgNotifyRelation {

}
