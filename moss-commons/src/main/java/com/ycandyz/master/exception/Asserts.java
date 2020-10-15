package com.ycandyz.master.exception;

import com.ycandyz.master.api.IErrorCode;

import java.math.BigDecimal;

/**
 * 断言处理类，用于抛出各种API异常
 * Created by macro on 2020/2/27.
 */
public class Asserts {
    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(IErrorCode errorCode) {
        throw new ApiException(errorCode);
    }

    public static void isNull(String msg, Object... conditions) {
        for (Object obj : conditions) {
            if (obj != null) {
                throw new ApiException(msg);
            }
        }
    }

    public static void notNull(String msg, Object... conditions) {
        for (Object obj : conditions) {
            if (obj == null) {
                throw new ApiException(msg);
            }
        }
    }

    public static void eq(Object obj1, Object obj2, String msg) {
        if (obj1 == null || !obj1.equals(obj2)) {
            throw new ApiException(msg);
        }
    }

    public static void gt(Integer num1, Integer num2, String msg) {
        if (num1 == null || num2 == null || num1 <= num2) {
            throw new ApiException(msg);
        }
    }

    public static void ge(Integer num1, Integer num2, String msg) {
        if (num1 == null || num2 == null || num1 < num2) {
            throw new ApiException(msg);
        }
    }

    public static void gt(BigDecimal num1, BigDecimal num2, String msg) {
        if (num1 == null || num2 == null || num1.compareTo(num2) <= 0) {
            throw new ApiException(msg);
        }
    }

    public static void ge(BigDecimal num1, BigDecimal num2, String msg) {
        if (num1 == null || num2 == null || num1.compareTo(num2) < 0) {
            throw new ApiException(msg);
        }
    }
}
