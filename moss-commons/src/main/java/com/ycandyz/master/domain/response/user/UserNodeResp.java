package com.ycandyz.master.domain.response.user;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * @Description 用户资源 Resp
 * </p>
 *
 * @author SanGang
 * @since 2020-10-30
 * @version 2.0
 */
@Getter
@Setter
public class UserNodeResp implements Serializable {

    @ApiModelProperty(value = "0-企业 1-前端 2-代理商 3-有传 4-U客后台 5-U客App")
    private Integer platform;

    @ApiModelProperty(value = "企业id")
    private Long organizeId;

    @ApiModelProperty(value = "菜单id")
    private Long menuId;

    @ApiModelProperty(value = "0 get/1 post/2 put/3 delete")
    private String methodType;

    @ApiModelProperty(value = "路由地址")
    private String uri;

}
