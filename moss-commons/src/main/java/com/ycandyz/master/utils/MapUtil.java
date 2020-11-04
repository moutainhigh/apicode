package com.ycandyz.master.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

public class MapUtil {

    public static <T extends Object> List<Map<String, Object>> beanToMap(List<T> list, List<String> containsList) {

        List<Map<String, Object>> result = new ArrayList<>();
        list.forEach(t -> {
            Map<String, Object> map = new HashMap<>();
            Class c = t.getClass();
            Field[] fields = c.getDeclaredFields();
            for (Field field : fields) {
                //打开私有访问
                field.setAccessible(true);
                String name = field.getName();
                if (containsList.indexOf(name) != -1) {
                    try {
                        Object o = field.get(t);
                        if (o instanceof List) {
                            List arr = (List) o;
                            String str = String.join("\r\n", arr);
                            map.put(name, str);
                        } else if (o instanceof BigDecimal){
                            BigDecimal b = (BigDecimal) o;
                            String str = b.toString();
                            map.put(name, str);
                        } else {
                            map.put(name, o);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            result.add(map);
        });
        System.out.println(result);
        return result;
    }
}
