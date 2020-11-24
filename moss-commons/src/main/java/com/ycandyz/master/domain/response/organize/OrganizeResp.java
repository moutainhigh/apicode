package com.ycandyz.master.domain.response.organize;

import com.ycandyz.master.entities.organize.Organize;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 组织表 Resp
 * </p>
 *
 * @author SanGang
 * @since 2020-11-09
 * @version 2.0
 */
@Getter
@Setter
@TableName("organize")
public class OrganizeResp extends Organize {

}
