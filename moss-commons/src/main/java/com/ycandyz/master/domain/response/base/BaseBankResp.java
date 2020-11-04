package com.ycandyz.master.domain.response.base;

import com.ycandyz.master.entities.base.BaseBank;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description base-银行 Resp
 * </p>
 *
 * @author SanGang
 * @since 2020-11-04
 * @version 2.0
 */
@Getter
@Setter
@TableName("base_bank")
public class BaseBankResp extends BaseBank {

}
