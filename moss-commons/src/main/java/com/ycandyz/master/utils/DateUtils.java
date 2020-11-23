package com.ycandyz.master.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 日期工具类
 * @author: WangYang
 * @Date: 2020/10/27 20:29
 * @Description:
 */
@Component
public class DateUtils {


    public final static String DEFAULT_PATTERN = "yyyy-MM-dd hh:mm:ss";

    public final static String YYYY_MM_DD = "yyyy-MM-dd";

    public static final ThreadLocal<SimpleDateFormat> DATE_FORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat(DEFAULT_PATTERN, Locale.CHINA));


    public static Date parseWithPattern(String strDate, String pattern) throws ParseException {
        SimpleDateFormat dateFormat = DATE_FORMAT.get();
        dateFormat.applyPattern(pattern);
        return dateFormat.parse(strDate);
    }

    /**
     * 获得当前日期 yyyy-MM-dd HH:mm:ss
     */
    public static Date getZeroZoneTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_PATTERN);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        DateTime dateTime = DateUtil.parse(dateFormat.format(DateUtil.date()), DEFAULT_PATTERN);
        return dateTime.toJdkDate();
    }

    /**
     *  为时间加一定天数
     * @param date
     * @param i 要加的天
     * @return
     */
    public static Date getAddDay(Date date,int i){
        if (date == null)
            return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.add(Calendar.DAY_OF_MONTH, i);
        return calendar.getTime();
    }
}
