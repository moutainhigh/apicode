package com.ycandyz.master.domain.response.organize;

import com.ycandyz.master.entities.organize.OrganizeGroup;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 集团表 Resp
 * </p>
 *
 * @author SanGang
 * @since 2020-10-23
 * @version 2.0
 */
@Getter
@Setter
@TableName("organize_group")
public class OrganizeGroupResp extends OrganizeGroup {

}
