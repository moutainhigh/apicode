package com.ycandyz.master.validation.annotaion;


import com.ycandyz.master.enums.IEnum;
import com.ycandyz.master.validation.validator.LengthValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 枚举校验注解.
 *
 * @author unuakoro
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {LengthValidator.class})
public @interface EnumValue {

    Class<? extends IEnum<Object>> enumClass();

    /**
     * 错误消息.
     *
     * @return 错误消息.
     */
    String message() default "参数错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
