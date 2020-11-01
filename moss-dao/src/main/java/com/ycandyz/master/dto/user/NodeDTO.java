package com.ycandyz.master.dto.user;

import com.ycandyz.master.entities.user.Node;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 权限节点表 实体类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-01
 * @version 2.0
 */
@Getter
@Setter
@TableName("node")
public class NodeDTO extends Node {

}
