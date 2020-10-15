package com.ycandyz.master.validation.annotaion;

import com.ycandyz.master.validation.validator.LengthValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 字段长度验证规则（非空时才验证）.
 *
 * @author 代子彬
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {LengthValidator.class})
public @interface Length {

    /**
     * 最小长度.
     * @return
     */
    int min() default 0;

    /**
     * 最大长度.
     * @return
     */
    int max() default Integer.MAX_VALUE;

    /**
     * 错误消息.
     * @return 错误消息.
     */
    String message() default "参数长度错误";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
