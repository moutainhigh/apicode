package com.ycandyz.master.utils;

import com.ycandyz.master.interfaces.CodeEnum;

public class EnumUtil {
    //返回的对象实现CodeEnum接口
    public static <T extends CodeEnum> T getByCode(Class<T> enumClass, Integer code) {
        for (T each : enumClass.getEnumConstants()) {
            if (each.getCode() == code) {
                return each;
            }
        }
        return null;

    }
}