package com.ycandyz.master.entities.mall.goodsManage;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Description: 商品分类表
 * @Author li Zhuo
 * @Date 2020/10/9
 * @Version: V1.0
*/
@Data
@TableName("mall_category")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="mall_category对象", description="商品分类表")
public class MallCategory {

    @TableId(type = IdType.UUID)
    @ApiModelProperty(value = "id")
    private long id;
    @ApiModelProperty(value = "商店编号")
    private String shopNo;
    @ApiModelProperty(value = "分类编号")
    private String categoryNo;
    @ApiModelProperty(value = "父分类编号")
    private String parentCategoryNo;
    @ApiModelProperty(value = "分类名称")
    private String categoryName;
    @ApiModelProperty(value = "分类图片")
    private String categoryImg;
    @ApiModelProperty(value = "分类处于第几级")
    private Integer layer;
    @ApiModelProperty(value = "排序值")
    private Integer sortValue;
    @ApiModelProperty(value = "状态：1: 正常，0: 无效  默认1")
    private boolean status;
    @ApiModelProperty(value = "updateTime")
    private Date createdTime;
    @ApiModelProperty(value = "updatedTime")
    private Date updatedTime;
}
