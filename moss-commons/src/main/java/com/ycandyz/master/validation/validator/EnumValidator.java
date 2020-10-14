package com.ycandyz.master.validation.validator;

import com.ycandyz.master.enums.IEnum;
import com.ycandyz.master.validation.annotaion.EnumValue;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 枚举校验.
 *
 * @author unuakoro
 */
public class EnumValidator implements ConstraintValidator<EnumValue, Object> {

    private Class<? extends IEnum<Object>> enumClass;

    @Override
    public void initialize(EnumValue constraintAnnotation) {
        enumClass = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (null != value) {
            return null != IEnum.parseByCode(value, enumClass);
        }
        return true;
    }
}
