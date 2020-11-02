package com.ycandyz.master.domain.response.user;

import com.ycandyz.master.entities.user.Node;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 权限节点表 Resp
 * </p>
 *
 * @author SanGang
 * @since 2020-11-01
 * @version 2.0
 */
@Getter
@Setter
@TableName("node")
public class NodeResp extends Node {

}
