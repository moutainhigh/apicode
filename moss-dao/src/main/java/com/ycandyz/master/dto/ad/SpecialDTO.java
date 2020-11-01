package com.ycandyz.master.dto.ad;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ycandyz.master.entities.ad.Special;
import com.ycandyz.master.entities.mall.MallItem;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <p>
 * @Description 首页-专题 实体类
 * </p>
 *
 * @author Wang Yang
 * @since 2020-10-14
 * @version 2.0
 */
@Getter
@Setter
@TableName("ad_special")
public class SpecialDTO extends Special {

    private List<MallItem> items;

}
