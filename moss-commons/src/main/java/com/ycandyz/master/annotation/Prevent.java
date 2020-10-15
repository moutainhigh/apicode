package com.ycandyz.master.annotation;

import com.ycandyz.master.enums.OperateEnum;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

;

/**
 * @author sangang
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface Prevent {

    OperateEnum operate() default OperateEnum.UPDATE;

}
