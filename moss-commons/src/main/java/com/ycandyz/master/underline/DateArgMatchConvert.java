package com.ycandyz.master.underline;

import com.ycandyz.master.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @description 日期类型参数转换器
 * @author SanGang
 */
@Component
public class DateArgMatchConvert extends ArgMatchConvert<Date> {

    @Override
    public Date doHandleConvert(String value) throws Exception{
        if(StringUtils.isBlank(value)){
            return null;
        }

        if(NumberUtils.isDigits(value)){
            return new Date(Long.parseLong(value));
        }

        try{
            return DateUtils.DATE_FORMAT.get().parse(value);
        }catch(Exception e) {
            // 尝试使用 yyyy-MM-dd 格式来解析
            return DateUtils.parseWithPattern(value, DateUtils.YYYY_MM_DD);
        }
    }

    @Override
    public boolean match(Class<?> target) {
        return Date.class.isAssignableFrom(target);
    }
}