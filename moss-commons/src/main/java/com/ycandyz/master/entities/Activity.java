package com.ycandyz.master.entities;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Awy
 * @create 2020-09-10 10:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="活动实体类", description="活动")
public class Activity implements Serializable {

    @ApiModelProperty(value="活动编号",name="id",required=true)
    private Integer id;

    @ApiModelProperty(value="活动名称",name="name",required=true)
    private String name;

    @ApiModelProperty(value="活动开始时间",name="beginAt",required=true)
    private Integer beginAt;

    @ApiModelProperty(value="活动结束时间",name="endAt",required=true)
    private Integer endAt;

    @ApiModelProperty(value="活动进行状态：0待开启 1启用 2禁用 3删除",name="status",required=true)
    private Integer status;

    @ApiModelProperty(value="活动创建时间",name="createdAt",required=true)
    private Integer createdAt;
}
