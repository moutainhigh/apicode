package com.ycandyz.master.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class DateUtil {

    public static final String DEFAULT_DAY_PATTERN = "yyyy-MM-dd hh:mm:ss";

    public static Date defaultParseDate(String dateStr) {
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DAY_PATTERN);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
        }
        return null;
    }


    public static String defaultFormatDate(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DAY_PATTERN);
        return sdf.format(date);
    }
}
