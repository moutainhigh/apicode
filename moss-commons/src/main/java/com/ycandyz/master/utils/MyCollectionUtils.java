package com.ycandyz.master.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

public class MyCollectionUtils {

    public static String PraseArraytoString(Object[] objects) {
        if (objects == null){
            return null;
        }
        List<Object> lists = Lists.newArrayList();
        Arrays.stream(objects).forEach(s -> lists.add(s));
        return lists+"";
    }

    public static List<String> removeNullString(String[] objects) {
        if (objects == null){
            return null;
        }
        List<String> lists = Lists.newArrayList();
        Arrays.stream(objects)
                .filter(s-> !StringUtils.isBlank(s))
                .forEach(s -> lists.add(s));
        return lists;
    }

    public static List<String> parseIds(String s) {
        int beginIndex = s.indexOf("[") == 0 ? 1 : 0;
        int endIndex = s.lastIndexOf("]") + 1 == s.length() ? s.lastIndexOf("]") : s.length();
        s = s.substring(beginIndex, endIndex);
        List<String> ids = Lists.newArrayList();
        if (s.length() <= 0){
            return ids;
        }
        Arrays.stream(s.split(",")).forEach(s1 -> ids.add(s1.trim()));
        return ids;
    }


}
