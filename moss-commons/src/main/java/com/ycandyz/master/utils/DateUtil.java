package com.ycandyz.master.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Slf4j
public class DateUtil {

    public static final String DEFAULT_DAY_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_YMD_PATTERN = "yyyyMMdd";
    public static final String DEFAULT_YMDHM_PATTERN = "yyyyMMdd HH:mm";
    /**
     * @Description: String to date
     * @Author li Zhuo
     * @Date 2020/10/22
     * @Version: V1.0
    */
    public static Date defaultParseDate(String dateStr) {
        Date date = null;
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DAY_PATTERN);
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            log.error("string转date错误");
        }
        return date;
    }

    /**
     * @Description:  date to String
     * @Author li Zhuo
     * @Date 2020/10/22
     * @Version: V1.0
    */
    public static String defaultFormatDate(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DAY_PATTERN);
        return sdf.format(date);
    }

    public static String formatDateForYMD(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_YMD_PATTERN);
        return sdf.format(date);
    }

    /**
     * @Description: 加8小时
     * @Author li Zhuo
     * @Date 2020/10/22
     * @Version: V1.0
    */
    public static String addEightHoursDateToString(Date date) {
        if (date == null) {
            return null;
        }
        long time = date.getTime();
        long eightHours = 8*60*60*1000;

        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DAY_PATTERN);
        return sdf.format(new Date(time+eightHours));
    }

    /**
     * @Description: 减8小时
     * @Author li Zhuo
     * @Date 2020/10/22
     * @Version: V1.0
     */
    public static Long reduceEightHours(Long data) {
        if (data == null) {
            return null;
        }
        long eightHours = 8*60*60;
        return  data - eightHours;
    }

    /*
     * 将时间戳转换为时间
     */
    public static Date stampToDate(Long stap){
        if (stap == null) {
            return new Date();
        }
        return new Date(stap * 1000L-8*60*60*1000);
    }


    public static Date getNowDateShort() {
        Calendar c = Calendar.getInstance();
        Date now = c.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(now);
        try {
            now = simpleDateFormat.parse(format);
        } catch (ParseException e) {
            log.error("时间转换失败");
        }
        return now;
    }


    /*
     * 转yyyyMMdd HH:mm:
     */
//    public static Date toymdhm(Date date){
//        if (date == null) {
//            return null;
//        }
//        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_YMDHM_PATTERN);
//        String format = sdf.format(date);
//        return sdf.format(new Date(time+eightHours));
//    }

}
