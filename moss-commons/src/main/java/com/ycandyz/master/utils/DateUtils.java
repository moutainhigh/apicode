package com.ycandyz.master.utils;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
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

    public static Date getCurrentDate(){
        return new Date();
    }

    /**
     * 获取当前时间Long类型
     */
    public static Long getCurrentSeconds() {
        return DateUtil.currentSeconds();
    }

    /**
     * 10位long类型转Date
     */
    public static Date getCurrentDate(Long dateSeconds){
        return DateTime.of(dateSeconds*1000L);
    }

    public static Long getCloseAt(Long closeAt) {
        AssertUtils.notNull(closeAt, "时间不能为空");
        DateTime dateTimeReceive = DateUtil.offset(DateTime.of(closeAt*1000L), DateField.DAY_OF_WEEK, 0);
        return DateUtil.between(dateTimeReceive,DateTime.now(), DateUnit.SECOND);
    }

    public static String queryEndDate(Date date){
        if(null == date){
            return null;
        }
        return DateUtil.formatDate(DateUtil.offset(date, DateField.DAY_OF_WEEK, 1));
    }
}
