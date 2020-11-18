package com.ycandyz.master.domain.query.miniprogram;

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
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * @Description 小程序转交接申请 检索参数类
 * </p>
 *
 * @author Wang Yang
 * @since 2020-11-16
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="小程序转交接申请-检索参数")
public class MpTransferApplyQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "企业编号")
    @Condition(field = "organize_id", condition = ConditionEnum.EQ)
    private Integer organizeId;

    @ApiModelProperty(value = "联系人姓名")
    @Condition(field = "contact_name", condition = ConditionEnum.LIKE)
    private String contactName;

    @ApiModelProperty(value = "联系人电话")
    @Condition(field = "contact_phone", condition = ConditionEnum.LIKE)
    private String contactPhone;

    @ApiModelProperty(value = "对接状态0、待对接；1、对接中；2、已完成")
    @Condition(field = "butted_status", condition = ConditionEnum.GE)
    private Integer buttedStatus;

    @ApiModelProperty(value = "申请时间起")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Condition(field = "create_time", condition = ConditionEnum.GE)
    private Date applyTimeStart;
    
    @ApiModelProperty(value = "申请时间止")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Condition(field = "create_time", condition = ConditionEnum.LE)
    private Date applyTimeEnd;

}
