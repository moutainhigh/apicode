package com.ycandyz.master.dto.sms;

import com.ycandyz.master.entities.sms.SmsSendQueueLog;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * <p>
 * @Description  实体类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-12
 * @version 2.0
 */
@Getter
@Setter
@TableName("sms_send_queue_log")
public class SmsSendQueueLogDTO extends SmsSendQueueLog {

    private Long id;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "消息模版code")
    private String templateCode;

    @ApiModelProperty(value = "阿里发送消息流水号")
    private String bizId;

    @ApiModelProperty(value = "0:推送中；1:成功；2:失败")
    private Integer state;

    @ApiModelProperty(value = "短信发送队列返回错误日志")
    private String log;

    @ApiModelProperty(value = "创建时间戳")
    private Long createdAt;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;
}
