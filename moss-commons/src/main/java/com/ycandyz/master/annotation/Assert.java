package com.ycandyz.master.annotation;

import com.ycandyz.master.enums.AssertEnum;
import com.ycandyz.master.enums.OperateEnum;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author sangang
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface Assert {

    AssertEnum type() default AssertEnum.NOT_NULL;

    OperateEnum operate() default OperateEnum.INSERT;

    int numValue() default 0;

    String strValue() default "";

    String regEx() default "";

}
