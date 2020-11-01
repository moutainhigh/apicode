package com.ycandyz.master.service.user.impl;

import com.ycandyz.master.entities.user.Node;
import com.ycandyz.master.domain.query.user.NodeQuery;
import com.ycandyz.master.dao.user.NodeDao;
import com.ycandyz.master.service.user.INodeService;
import com.ycandyz.master.controller.base.BaseService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * @Description 权限节点表 业务类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-01
 * @version 2.0
 */
@Slf4j
@Service
public class NodeServiceImpl extends BaseService<NodeDao,Node,NodeQuery> implements INodeService {

    @Override
    public List<String> getAllNode() {
        return baseMapper.getAllNode();
    }
}
