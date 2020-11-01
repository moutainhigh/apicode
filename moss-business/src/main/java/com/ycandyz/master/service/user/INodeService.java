package com.ycandyz.master.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycandyz.master.entities.user.Node;

import java.util.List;

/**
 * <p>
 * @Description 权限节点表 业务接口类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-01
 * @version 2.0
 */
public interface INodeService extends IService<Node>{

    List<String> getAllNode();
	
}
