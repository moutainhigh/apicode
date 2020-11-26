package com.ycandyz.master.dto.msg;

import com.ycandyz.master.entities.msg.MsgNotify;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 业务通知表 实体类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-13
 * @version 2.0
 */
@Getter
@Setter
@TableName("msg_notify")
public class MsgNotifyDTO extends MsgNotify {

}
