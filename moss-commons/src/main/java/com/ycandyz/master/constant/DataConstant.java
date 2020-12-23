package com.ycandyz.master.constant;

import java.util.HashMap;
import java.util.Map;

public final class DataConstant {
    private DataConstant() {
    }

    //模板内容来源
    public static final String CONTENT_CHANNEL_OFFICIAL_ACCOUNTS = "0";
    public static final String CONTENT_CHANNEL_PUBLIC_NUMBER = "1";
    public static final String CONTENT_CHANNEL_APP = "2";
    public static final Map<String, String> CONTENT_CHANNEL_MAP = new HashMap<>();

    static {
        CONTENT_CHANNEL_MAP.put(CONTENT_CHANNEL_OFFICIAL_ACCOUNTS, "小程序");
        CONTENT_CHANNEL_MAP.put(CONTENT_CHANNEL_PUBLIC_NUMBER, "公众号");
        CONTENT_CHANNEL_MAP.put(CONTENT_CHANNEL_APP, "app");
    }

    //模板平台系统
    public static final String CONTENT_PLATFORM_ANDROID = "0";
    public static final String CONTENT_PLATFORM_IOS = "1";
    public static final Map<String, String> CONTENT_PLATFORM_MAP = new HashMap<>();

    static {
        CONTENT_PLATFORM_MAP.put(CONTENT_PLATFORM_ANDROID, "android");
        CONTENT_PLATFORM_MAP.put(CONTENT_PLATFORM_IOS, "ios");
    }

    //模板平台类型
    public static final String TEMPLATE_PLATFORM_OFFICIAL_ACCOUNTS = "0";
    public static final String TEMPLATE_PLATFORM_PC = "1";

    //模板失效状态
    public static final int TEMPLATE_STATUS_NORMAL = 0;//正常
    public static final int TEMPLATE_STATUS_INVALID = 1;//失效
}
