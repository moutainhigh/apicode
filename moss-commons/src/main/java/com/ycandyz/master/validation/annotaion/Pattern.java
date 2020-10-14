package com.ycandyz.master.validation.annotaion;

import com.ycandyz.master.validation.validator.PatternValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author wef
 * @create 2019-01-26
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {PatternValidator.class})
public @interface Pattern {
    /**
     * 错误消息.
     *
     * @return 错误消息.
     */
    String message();

    /**
     * 正则表达式
     *
     * @return
     */
    String regex();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
