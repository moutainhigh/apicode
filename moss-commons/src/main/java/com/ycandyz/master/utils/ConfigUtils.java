package com.ycandyz.master.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * 读取配置文件属性工具类
 * SanGang
 */
@Configuration
public class ConfigUtils implements ApplicationContextAware{
    private static Environment env;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        env = applicationContext.getBean(Environment.class);
    }
    public static String getValue(String key){
        return env.getProperty(key);
    }

    public static boolean getBoolean(String key){
        return getBoolean(key,false);
    }

    public static boolean getBoolean(String key,boolean throwException){
        String result = env.getProperty(key);
        if (throwException) {
            if (result == null || result.isEmpty()) {
                throw new NullPointerException();
            }
        }
        return result != null ? Boolean.valueOf(result):Boolean.FALSE;
    }


}
