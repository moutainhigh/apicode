package com.ycandyz.master.underline;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.core.MethodParameter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Stream;

/**
 * @description 将请求参数的下划线格式转为驼峰格式
 * @author SanGang
 */
@Slf4j
public class CustomHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final static String UNDER_LINE = "_";

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        // 如果参数上标注了 UnderlineToHump 注解，则说明需要将下换线换驼峰
        return methodParameter.hasParameterAnnotation(Hump.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        Object obj = BeanUtils.instantiateClass(methodParameter.getParameterType());
        Iterator<String> iterator = nativeWebRequest.getParameterNames();
        // Map<对象的属性名称, 值>
        Map<String, String> paramValues = new HashMap<>();
        while (iterator.hasNext()) {
            String param = iterator.next();
            String value = nativeWebRequest.getParameter(param);
            paramValues.put(underLine2HumpForArgs(param), value);
        }
        // 递归解析对象属性并赋值
        parseAttributes(obj, paramValues);
        return obj;
    }

    /**
     * 解析对象属性并赋值
     *
     * @param obj 顶层对象
     * @param paramValues <code>Map<属性名, 值></code>
     */
    private void parseAttributes(Object obj, Map<String, String> paramValues) {
        Field[] fields = obj.getClass().getDeclaredFields();
        BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(obj);
        Stream.of(fields).forEach(field -> {
            String fName = field.getName();
            try {
                // 如果存在字段存在该注解，则说明需要对该属性就行内部解析
                SubAttribute subModel = field.getAnnotation(SubAttribute.class);
                if (Objects.nonNull(subModel)) {
                    Object tmp = BeanUtils.instantiateClass(field.getType());
                    parseAttributes(tmp, paramValues);
                    //  ReflectionUtils.setField(field,obj,tmp);
                    wrapper.setPropertyValue(fName, tmp);
                    return;
                }

                if (!CollectionUtils.isEmpty(paramValues) && paramValues.containsKey(fName)) {
                    //  ReflectionUtils.setField(field,obj,paramValues.get(fName));
                    String strValue = paramValues.get(fName);
                    List<ArgMatchConvert<?>> converts = ArgMatchConvertHolder.getConverts();
                    if(!CollectionUtils.isEmpty(converts)){
                        Optional<ArgMatchConvert<?>> optional = converts.stream().filter(convert -> convert.match(wrapper.getPropertyType(fName))).findFirst();
                        if(optional.isPresent()){
                            wrapper.setPropertyValue(fName, optional.get().doHandleConvert(strValue));
                            return;
                        }
                    }

                    wrapper.setPropertyValue(fName, strValue);
                }
            } catch (Exception e) {
                e.printStackTrace();
                // 注意记录异常日志
            }
        });
    }

    /**
     * 下划线转驼峰并且首字母小写
     *
     * @param source 原字符串
     * @return 处理后的字符串
     */
    private String underLine2HumpForArgs(String source) {
        while (source.contains(UNDER_LINE)) {
            int idx = source.indexOf(UNDER_LINE);
            source = source.replaceFirst(UNDER_LINE, "");
            // 下标不是最后一位
            if (idx != -1 && idx < source.length() - 1) {
                char[] chs = source.toCharArray();
                chs[idx] = Character.toUpperCase(chs[idx]);
                // 首字母小写
                chs[0] = Character.toLowerCase(chs[0]);
                source = String.valueOf(chs);
            }
        }
        return source;
    }
}
