package com.ycandyz.master.api;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 统一封装请求传入对象
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="请求入参统一封装类")
public class RequestParams<T> {

    /**
     * 每页显示条数，默认 10
     */
    private long page_size = 10;

    /**
     * 当前页
     */
    private long page = 1;

    /**
     * 请求传入实体对象
     */
    private T t;

    /**
     * 请求传入jsonString数据
     */
    private String data;
}
