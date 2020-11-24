package com.ycandyz.master.model.sms;

import com.ycandyz.master.entities.sms.SmsSendQueueLog;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description  VO
 * </p>
 *
 * @author SanGang
 * @since 2020-11-24
 * @version 2.0
 */
@Getter
@Setter
@TableName("sms_send_queue_log")
public class SmsSendQueueLogVO extends SmsSendQueueLog {

}
