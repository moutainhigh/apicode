package com.ycandyz.master.underline;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description 作用于接口参数，表示该参数需要进行下划线转驼峰 UnderlineToHump
 * @author SanGang
 */


@Target(value = ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Hump {
}
