package com.ycandyz.master.domain.model.msg;

import com.ycandyz.master.annotation.Condition;
import com.ycandyz.master.enums.ConditionEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * @Description 业务通知表 检索参数类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-13
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="业务通知-参数")
public class MsgNotifyModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "业务通知表主键")
    @Condition(field = "id", condition = ConditionEnum.EQ)
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
