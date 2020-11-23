package com.ycandyz.master.underline;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description 作用于属性字段，表明该字段是自定义对象，需要进行该属性对象的属性解析
 * @author SanGang
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SubAttribute {
}