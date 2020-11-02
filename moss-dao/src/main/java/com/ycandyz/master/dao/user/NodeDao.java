package com.ycandyz.master.dao.user;

import com.ycandyz.master.entities.user.Node;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 权限节点表 Mapper 接口
 * </p>
 *
 * @author SanGang
 * @since 2020-11-01
 */
public interface NodeDao extends BaseMapper<Node> {

    List<String> getAllNode();

}
