package com.ycandyz.master.dto.ad;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ycandyz.master.entities.ad.Advertising;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 轮播图 实体类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-13
 * @version 2.0
 */
@Getter
@Setter
@TableName("ad_advertising")
public class AdvertisingDTO extends Advertising {

}
