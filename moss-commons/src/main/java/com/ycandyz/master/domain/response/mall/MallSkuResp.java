package com.ycandyz.master.domain.response.mall;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ycandyz.master.entities.mall.MallSku;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description sku表 Resp
 * </p>
 *
 * @author Wang Yang
 * @since 2020-10-21
 * @version 2.0
 */
@Getter
@Setter
@TableName("mall_sku")
public class MallSkuResp extends MallSku {

}
