package com.ycandyz.master.api;

import cn.hutool.core.date.DateUtil;
import lombok.Data;

@Data
public class OtherResult<T> {
    private long code;
    private String msg;
    private T data;
    private T Other;
    private long time = DateUtil.currentSeconds();

    public OtherResult() {
    }

    public OtherResult(long code,String msg,T data,T other) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        Other = other;
    }


    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> OtherResult<T> success(T data,T other) {
        return new OtherResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data,other);
    }
}
