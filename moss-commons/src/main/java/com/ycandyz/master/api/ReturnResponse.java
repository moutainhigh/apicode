package com.ycandyz.master.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnResponse<T> {

    private Long    code;
    private String  msg;
    private Long    time;
    private T       data;

    public ReturnResponse(Long code, String message)
    {
        this(code,message,new Date().getTime()/1000,null);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> ReturnResponse<T> success(T data) {
        return new ReturnResponse<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), new Date().getTime()/1000, data);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     * @param  message 提示信息
     */
    public static <T> ReturnResponse<T> success(T data, String message) {
        return new ReturnResponse<T>(ResultCode.SUCCESS.getCode(), message, new Date().getTime()/1000, data);
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     */
    public static <T> ReturnResponse<T> failed(IErrorCode errorCode) {
        return new ReturnResponse<T>(errorCode.getCode(), errorCode.getMessage(), new Date().getTime()/1000, null);
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     * @param message 错误信息
     */
    public static <T> ReturnResponse<T> failed(IErrorCode errorCode, String message) {
        return new ReturnResponse<T>(errorCode.getCode(), message, new Date().getTime()/1000, null);
    }

    /**
     * 失败返回结果
     *
     * @param data 获取的数据
     */
    public static <T> ReturnResponse<T> failed(T data) {
        return new ReturnResponse<T>(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMessage(), new Date().getTime()/1000, data);
    }

    /**
     * 失败返回结果
     * @param message 提示信息
     */
    public static <T> ReturnResponse<T> failed(String message) {
        return new ReturnResponse<T>(ResultCode.FAILED.getCode(), message, new Date().getTime()/1000, null);
    }

    /**
     * 失败返回结果
     */
    public static <T> ReturnResponse<T> failed() {
        return failed(ResultCode.FAILED);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> ReturnResponse<T> validateFailed() {
        return failed(ResultCode.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     * @param message 提示信息
     */
    public static <T> ReturnResponse<T> validateFailed(String message) {
        return new ReturnResponse<T>(ResultCode.VALIDATE_FAILED.getCode(), message, new Date().getTime()/1000, null);
    }

    /**
     * 未登录返回结果
     */
    public static <T> ReturnResponse<T> unauthorized(T data) {
        return new ReturnResponse<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), new Date().getTime()/1000, data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> ReturnResponse<T> forbidden(T data) {
        return new ReturnResponse<T>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), new Date().getTime() / 1000, data);
    }
}
