package com.ycandyz.master.entities.mall.goodsManage;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.poi.hpsf.Decimal;

import java.util.Date;

@Data
@TableName("mall_sku_spec")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="mall_sku_spec对象", description="skuSpec表")
public class MallSkuSpec {

    @TableId(type = IdType.UUID)
    @ApiModelProperty(value = "id")
    private long id;

    @ApiModelProperty(value = "sku编号")
    private String skuNo;

    @ApiModelProperty(value = "名称")
    private String specName;

    @ApiModelProperty(value = "值")
    private String specValue;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;

}
