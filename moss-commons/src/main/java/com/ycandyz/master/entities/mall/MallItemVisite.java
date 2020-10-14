package com.ycandyz.master.entities.mall;

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
 * @Description: 名片浏览表
 * @Author: Wang Yang
 * @Date:   2020-09-20
 * @Version: V1.0
 */
@Data
@TableName("mall_item_visite")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="mall_item_visite对象", description="商品浏览表")
public class MallItemVisite {
    
	/**id*/
	@TableId(type = IdType.UUID)
    @ApiModelProperty(value = "id")
	private Integer id;
	/**浏览用户id*/
    @ApiModelProperty(value = "浏览用户id")
	private Integer userId;
	/**卖家用户id*/
    @ApiModelProperty(value = "卖家用户id")
	private Integer sellerUserId;
	/**商品号*/
    @ApiModelProperty(value = "商品号")
	private String itemNo;
	/**商品名称*/
    @ApiModelProperty(value = "商品名称")
	private String itemName;
	/**卖家企业id*/
    @ApiModelProperty(value = "卖家企业id")
	private Integer sellerOrganizeId;
	/**updateTime*/
    @ApiModelProperty(value = "updateTime")
	private Date updateTime;
	/**createTime*/
    @ApiModelProperty(value = "createTime")
	private Date createTime;
}
