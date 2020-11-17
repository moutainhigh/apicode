package com.ycandyz.master.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtils {

    //图片正则匹配
    public static String reg(String line) {
        Pattern pattern = Pattern.compile("https://(?!(\\.jpg|\\.png|\\.jpeg)).+?(\\.jpg|\\.png|\\.jpeg)");
        Matcher matcher = pattern.matcher(line);
        String url = "";
        if (matcher.find()) {
            url = matcher.group(0);
        }
        return url;
    }
}
