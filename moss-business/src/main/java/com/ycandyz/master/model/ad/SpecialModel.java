package com.ycandyz.master.model.ad;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ycandyz.master.entities.ad.Special;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <p>
 * @Description 首页-专题 VO
 * </p>
 *
 * @author SanGang
 * @since 2020-10-14
 * @version 2.0
 */
@Getter
@Setter
@TableName("ad_special")
public class SpecialModel extends Special {

    @ApiModelProperty(value = "商品编号")
    private List<String> itemNoList;
}
