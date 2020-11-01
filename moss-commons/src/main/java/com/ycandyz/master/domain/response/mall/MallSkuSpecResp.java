package com.ycandyz.master.domain.response.mall;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ycandyz.master.entities.mall.MallSkuSpec;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description sku值表 Resp
 * </p>
 *
 * @author SanGang
 * @since 2020-10-20
 * @version 2.0
 */
@Getter
@Setter
@TableName("mall_sku_spec")
public class MallSkuSpecResp extends MallSkuSpec {

}
