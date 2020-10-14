package com.ycandyz.master.entities;

import com.ycandyz.master.enmus.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther wy
 * @create 2020-09-10 10:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T>
{
    private Integer code;
    private String  message;
    private T       data;

    public CommonResult(Integer code, String message)
    {
        this(code,message,null);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(ResultEnum.SUCCESS.getValue(), ResultEnum.SUCCESS.getDesc(), data);
    }
    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> CommonResult<T> failed(T data) {
        return new CommonResult<T>(ResultEnum.FAILED.getValue(), ResultEnum.FAILED.getDesc(), data);
    }
}
