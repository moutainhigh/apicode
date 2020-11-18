package com.ycandyz.master.dto.miniprogram;

import com.ycandyz.master.entities.miniprogram.MpTransferApply;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 小程序转交接申请 实体类
 * </p>
 *
 * @author Wang Yang
 * @since 2020-11-16
 * @version 2.0
 */
@Getter
@Setter
@TableName("mp_transfer_apply")
public class MpTransferApplyDTO extends MpTransferApply {

}
