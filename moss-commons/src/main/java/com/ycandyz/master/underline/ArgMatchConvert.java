package com.ycandyz.master.underline;

import org.springframework.beans.factory.InitializingBean;

/**
 * @description 参数转换类型匹配装转换器抽象
 *
 * @author SanGang
 */
public abstract class ArgMatchConvert<T> implements InitializingBean {

    /**
     * 转换核心逻辑
     *
     * @param source 待转换的值
     * @return T
     * @exception Exception 转换发生异常时抛出
     */
    public abstract T doHandleConvert(String source) throws Exception;

    /**
     * 是否匹配
     *
     * @param target 目标配类型
     * @return boolean
     */
    public abstract boolean match(Class<?> target);

    @Override
    public void afterPropertiesSet() throws Exception{
        ArgMatchConvertHolder.register(this);
    }
}