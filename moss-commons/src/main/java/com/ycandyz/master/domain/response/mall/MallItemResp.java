package com.ycandyz.master.domain.response.mall;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ycandyz.master.entities.mall.MallItem;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 商品表 Resp
 * </p>
 *
 * @author SanGang
 * @since 2020-10-17
 * @version 2.0
 */
@Getter
@Setter
@TableName("mall_item")
public class MallItemResp extends MallItem {

    @ApiModelProperty(value = "用户数量统计")
    private Long userCount;

}
