package com.ycandyz.master.utils;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

public class CollectionUtils {

    public static String PraseArraytoString(Object[] objects) {
        if (objects == null){
            return null;
        }
        List<Object> lists = Lists.newArrayList();
        Arrays.stream(objects).forEach(s -> lists.add(s));
        return lists+"";
    }
}
