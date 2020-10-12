package com.ycandyz.master.utils;

import java.util.UUID;

/**
 * @author: WangYang
 * @Date: 2020/9/28 10:20
 * @Description:
 */
public class TraceIdUtil {

    public static String getTraceId() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }
}
