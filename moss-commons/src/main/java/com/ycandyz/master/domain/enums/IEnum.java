package com.ycandyz.master.domain.enums;

/**
 * @author SanGang
 */
public interface IEnum<T> {

    T getCode();

    String getText();


    /**
     * 根据 code 获取枚举值.
     *
     * @param value code
     * @param type  枚举类型
     * @param <E>   枚举类型
     * @param <T>   code 类型
     * @return 对应枚举
     */
    static <E extends IEnum<T>, T> E parseByCode(T value, Class<E> type) {
        if (null != value && type.isEnum()) {
            for (E enumConstant : type.getEnumConstants()) {
                if (enumConstant.getCode().equals(value)) {
                    return enumConstant;
                }
            }
        }
        return null;
    }

    /**
     * 根据 code 获取枚举值.
     *
     * @param value code
     * @param type  枚举类型
     * @param <E>   枚举类型
     * @param <T>   code 类型
     * @return 对应枚举
     */
    static <E extends IEnum<T>, T> E parseByCode(T value, Class<E> type, E defaultValue) {
        if (null != value && type.isEnum()) {
            for (E enumConstant : type.getEnumConstants()) {
                if (enumConstant.getCode().equals(value)) {
                    return enumConstant;
                }
            }
        }
        return defaultValue;
    }

    /**
     * 根据 code 获取枚举 Text 值.
     *
     * @param value code
     * @param type  枚举类型
     * @param <E>   枚举类型
     * @param <T>   code 类型
     * @return 对应枚举
     */
    static <E extends IEnum<T>, T> String getTextByCode(T value, Class<E> type) {
        if (null != value && type.isEnum()) {
            for (E enumConstant : type.getEnumConstants()) {
                if (enumConstant.getCode().equals(value)) {
                    return enumConstant.getText();
                }
            }
        }
        return "";
    }

    /**
     * 根据 code 获取枚举 Text 值.
     *
     * @param value        code
     * @param type         枚举类型
     * @param defaultValue 默认枚举
     * @param <E>          枚举类型
     * @param <T>          code 类型
     * @return 枚举 text
     */
    static <E extends IEnum<T>, T> String getTextByCode(T value, Class<E> type, E defaultValue) {
        if (null != value && type.isEnum()) {
            for (E enumConstant : type.getEnumConstants()) {
                if (enumConstant.getCode().equals(value)) {
                    return enumConstant.getText();
                }
            }
        }
        return defaultValue.getText();
    }

}
