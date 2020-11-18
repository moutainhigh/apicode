package com.ycandyz.master.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *   接口返回数据格式
 */
@Data
@ApiModel(value="Result对象", description="接口返回对象")
public class BaseResult<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 返回数据对象 data
	 */
	@ApiModelProperty(value = "返回数据对象")
	private T result;

	public BaseResult(T result) {
		this.result = result;
	}

}