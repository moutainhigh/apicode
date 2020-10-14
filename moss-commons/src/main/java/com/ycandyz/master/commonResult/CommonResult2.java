package com.ycandyz.master.commonResult;

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
public class CommonResult2<T> {

    private T  data;

    /**
     * 成功返回结果
     */

    public static <T> CommonResult2 success() {
        return new CommonResult2<Result>(new Result(ResultEnum.SUCCESS.getValue(), ResultEnum.SUCCESS.getDesc()));
    }
    public static <T> CommonResult2 failed() {
        return new CommonResult2<Result>(new Result(ResultEnum.FAILED.getValue(), ResultEnum.FAILED.getDesc()));
    }

    public static <T> CommonResult2<T> success(T t){
        return new CommonResult2<T>(t);
    }
}
