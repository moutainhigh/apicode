package com.ycandyz.master.domain.response.menu;

import com.ycandyz.master.entities.menu.MenuNode;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 菜单关联权限表 Resp
 * </p>
 *
 * @author SanGang
 * @since 2020-10-21
 * @version 2.0
 */
@Getter
@Setter
@TableName("menu_node")
public class MenuNodeResp extends MenuNode {

}
