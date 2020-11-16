package com.ycandyz.master.dto.miniprogram;

import com.ycandyz.master.entities.miniprogram.MpConfigMenu;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 小程序配置-菜单配置 实体类
 * </p>
 *
 * @author WangYang
 * @since 2020-11-13
 * @version 2.0
 */
@Getter
@Setter
@TableName("mp_config_menu")
public class MpConfigMenuDTO extends MpConfigMenu {

}
