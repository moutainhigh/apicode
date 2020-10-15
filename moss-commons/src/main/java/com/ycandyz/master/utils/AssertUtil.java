package com.ycandyz.master.utils;

import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.ycandyz.master.annotation.Assert;
import com.ycandyz.master.enums.OperateEnum;
import com.ycandyz.master.exception.Asserts;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ycandyz.master.enums.AssertEnum.*;
import static com.ycandyz.master.enums.AssertEnum.EQ;
import static com.ycandyz.master.enums.AssertEnum.GE;
import static com.ycandyz.master.enums.AssertEnum.GT;
import static com.ycandyz.master.enums.AssertEnum.LE;
import static com.ycandyz.master.enums.AssertEnum.LT;
import static com.ycandyz.master.enums.AssertEnum.NOT_NULL;
import static com.ycandyz.master.enums.AssertEnum.NULL;
import static com.ycandyz.master.enums.AssertEnum.REGEX;

/**
 * @author sangang
 */
public class AssertUtil {

    // Insert时检验字段为空
    public static <T extends Model> void checkout(T entity, OperateEnum operate) {
        for (Field field : ReflectUtil.getAllFields(entity.getClass())) {
            field.setAccessible(true);
            // 校验条件标签
            Assert annotation = field.getAnnotation(Assert.class);
            if (annotation == null || !annotation.operate().equals(operate)) continue;
            // 校验字段的值
            Object value = null;
            try {
                value = field.get(entity);
            } catch (IllegalAccessException e) {
                break;
            }
            // 根据类型校验字段
            switch (annotation.type()) {
                case NOT_NULL:
                    Asserts.notNull(field.getName() + NOT_NULL.getDescription(), value);
                    break;
                case NULL:
                    Asserts.isNull(field.getName() + NULL.getDescription(), value);
                    break;
                case EQ:
                    Asserts.notNull(field.getName() + NOT_NULL.getDescription(), value);
                    if (!NumberUtil.isNumber(value.toString())) {
                        Asserts.eq(annotation.strValue(), value.toString(), field.getName() + EQ.getDescription() + annotation.strValue());
                    } else {
                        Asserts.eq(annotation.numValue(), Integer.valueOf(value.toString()).intValue(), field.getName() + EQ.getDescription() + annotation.numValue());
                    }
                    break;
                case GT:
                    Asserts.notNull(field.getName() + NOT_NULL.getDescription(), value);
                    Asserts.gt(Integer.valueOf(value.toString()).intValue(), annotation.numValue(), field.getName() + GT.getDescription() + annotation.numValue());
                    break;
                case GE:
                    Asserts.notNull(field.getName() + NOT_NULL.getDescription(), value);
                    Asserts.ge(Integer.valueOf(value.toString()).intValue(), annotation.numValue(), field.getName() + GE.getDescription() + annotation.numValue());
                    break;
                case LT:
                    Asserts.notNull(field.getName() + NOT_NULL.getDescription(), value);
                    Asserts.gt(annotation.numValue(), Integer.valueOf(value.toString()).intValue(), field.getName() + LT.getDescription() + annotation.numValue());
                    break;
                case LE:
                    Asserts.notNull(field.getName() + NOT_NULL.getDescription(), value);
                    Asserts.ge(annotation.numValue(), Integer.valueOf(value.toString()).intValue(), field.getName() + LE.getDescription() + annotation.numValue());
                    break;
                case REGEX:
                    Asserts.notNull(field.getName() + NOT_NULL.getDescription(), value);
                    Pattern pattern = Pattern.compile(annotation.regEx());
                    Matcher matcher = pattern.matcher(value.toString());
                    if (!matcher.matches()) {
                        Asserts.eq(0, 1, field.getName() + REGEX.getDescription() + annotation.regEx());
                    }
                    break;
                default:
                    break;
            }
        }
    }

}
