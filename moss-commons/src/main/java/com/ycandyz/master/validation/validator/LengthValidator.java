package com.ycandyz.master.validation.validator;

import com.ycandyz.master.validation.annotaion.Length;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 长度校验处理逻辑.
 *
 * @author 代子彬
 */
public class LengthValidator implements ConstraintValidator<Length, String> {

    /**
     * 包含值时最小长度.
     */
    private int min;

    /**
     * 包含值时最大长度.
     */
    private int max;

    @Override
    public void initialize(Length constraintAnnotation) {
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!StringUtils.isBlank(value)) {
            return value.trim().length() >= min && value.trim().length() <= max;
        }
        return true;
    }
}
