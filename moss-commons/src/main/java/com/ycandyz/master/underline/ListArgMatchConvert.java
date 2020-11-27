package com.ycandyz.master.underline;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description List参数处理转换器
 * @author SanGang
 */
@Component
public class ListArgMatchConvert extends ArgMatchConvert<List<?>>{

    @Override
    public List<?> doHandleConvert(String source) throws Exception {
        List target = new ArrayList<>();
        if(StringUtils.isBlank(source)){
            return target;
        }
        source = source.replaceAll("\\[","").replaceAll("]", "");
        if(StringUtils.isBlank(source)){
            return target;
        }
        if(!source.contains(",")){
            target.add(source);
        }

        return Arrays.stream(source.split(",")).collect(Collectors.toList());
    }

    @Override
    public boolean match(Class<?> target) {
        return List.class.isAssignableFrom(target);
    }
}