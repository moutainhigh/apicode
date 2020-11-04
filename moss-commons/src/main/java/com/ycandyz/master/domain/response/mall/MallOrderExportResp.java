package com.ycandyz.master.domain.response.mall;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: WangYang
 * @Date: 2020/10/15 16:59
 * @Description:
 */
@Data
public class MallOrderExportResp implements Serializable {

    @ApiModelProperty(value = "文件名")
    private String fileName;

    @ApiModelProperty(value = "s3文件下载地址")
    private String fielUrl;

}
