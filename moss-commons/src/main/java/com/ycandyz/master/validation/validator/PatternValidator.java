package com.ycandyz.master.validation.validator;

import com.ycandyz.master.validation.annotaion.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author wef
 * @create 2019-01-26
 */
public class PatternValidator implements ConstraintValidator<Pattern, String> {
    private static final Logger logger = LoggerFactory.getLogger(PatternValidator.class);

    private String regex;

    @Override
    public void initialize(Pattern constraintAnnotation) {
        regex = constraintAnnotation.regex();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isBlank(s)) {
            return true;
        }
        return java.util.regex.Pattern.matches(regex, s);
    }

}
