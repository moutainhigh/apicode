package com.ycandyz.master.domain.query.sms;

import com.ycandyz.master.annotation.Condition;
import com.ycandyz.master.enums.ConditionEnum;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * @Description  检索参数类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-12
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="-检索参数")
public class SmsSendQueueLogQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @Condition(field = "id", condition = ConditionEnum.EQ)
    private Long id;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "消息模版code")
    private String templateCode;

    @ApiModelProperty(value = "阿里发送消息流水号")
    private String bizId;

    @ApiModelProperty(value = "0:推送中；1:成功；2:失败")
    private String state;

    @ApiModelProperty(value = "短信发送队列返回错误日志")
    private String log;

    @ApiModelProperty(value = "创建时间戳")
    private Long createdAt;

    @ApiModelProperty(value = "创建时间起")
    @Condition(field = "created_time", condition = ConditionEnum.GE)
    private Date createdTimeS;
    
    @ApiModelProperty(value = "创建时间止")
    @Condition(field = "created_time", condition = ConditionEnum.LE)
    private Date createdTimeE;



}
