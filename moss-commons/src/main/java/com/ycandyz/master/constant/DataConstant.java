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
    public static final String TEMPLATE_PLATFORM_APP = "App";
    public static final String TEMPLATE_PLATFORM_MINIPROGRAM = "MiniProgram";
    public static final String TEMPLATE_PLATFORM_WEB = "web";

    //模板失效状态
    public static final int TEMPLATE_STATUS_NORMAL = 0;//正常
    public static final int TEMPLATE_STATUS_INVALID = 1;//失效

    public static final int TEMPLATE_COMPONENT_SIGN = 2;//单项填空
    public static final int TEMPLATE_COMPONENT_INPUT = 5;//输入框
    public static final Map<Integer, String> TEMPLATE_COMPONENT_MAP = new HashMap<>();

    static {
        TEMPLATE_COMPONENT_MAP.put(TEMPLATE_COMPONENT_SIGN, "单项填空");
        TEMPLATE_COMPONENT_MAP.put(TEMPLATE_COMPONENT_INPUT, "输入框");
    }

    public static final int MALL_ITEM_RECOMMEND_IS_RECOMMEND_NO = 0;//不推荐
    public static final int MALL_ITEM_RECOMMEND_IS_RECOMMEND_YES = 1;//推荐
    public static final Map<Integer, String> MALL_ITEM_RECOMMEND_IS_RECOMMEND_MAP = new HashMap<>();

    static {
        MALL_ITEM_RECOMMEND_IS_RECOMMEND_MAP.put(MALL_ITEM_RECOMMEND_IS_RECOMMEND_NO, "不推荐");
        MALL_ITEM_RECOMMEND_IS_RECOMMEND_MAP.put(MALL_ITEM_RECOMMEND_IS_RECOMMEND_YES, "推荐");
    }

    public static final int MALL_ITEM_RECOMMEND_WAY_AUTO = 1;//自动推荐
    public static final int MALL_ITEM_RECOMMEND_WAY_MANUAL = 2;//手动推荐
    public static final Map<Integer, String> MALL_ITEM_RECOMMEND_WAY_MAP = new HashMap<>();

    static {
        MALL_ITEM_RECOMMEND_WAY_MAP.put(MALL_ITEM_RECOMMEND_WAY_AUTO, "自动推荐");
        MALL_ITEM_RECOMMEND_WAY_MAP.put(MALL_ITEM_RECOMMEND_WAY_MANUAL, "手动推荐");
    }

    public static final String MALL_ITEM_RECOMMEND_SHOW_NAME = "推荐";

    public static final int MALL_ITEM_RECOMMEND_RECOMMEND_TYPE_NEW = 1;//最新添加的商品
    public static final int MALL_ITEM_RECOMMEND_RECOMMEND_TYPE_LOOK = 2;//全部商品浏览TOP
    public static final int MALL_ITEM_RECOMMEND_RECOMMEND_TYPE_SALE = 3;//全部商品销售TOP
    public static final int MALL_ITEM_RECOMMEND_RECOMMEND_TYPE_ORDER = 4;//全部商品排序TOP
    public static final Map<Integer, String> MALL_ITEM_RECOMMEND_RECOMMEND_TYPE_MAP = new HashMap<>();

    static {
        MALL_ITEM_RECOMMEND_RECOMMEND_TYPE_MAP.put(MALL_ITEM_RECOMMEND_RECOMMEND_TYPE_NEW, "最新添加的商品");
        MALL_ITEM_RECOMMEND_RECOMMEND_TYPE_MAP.put(MALL_ITEM_RECOMMEND_RECOMMEND_TYPE_LOOK, "全部商品浏览TOP");
        MALL_ITEM_RECOMMEND_RECOMMEND_TYPE_MAP.put(MALL_ITEM_RECOMMEND_RECOMMEND_TYPE_SALE, "全部商品销售TOP");
        MALL_ITEM_RECOMMEND_RECOMMEND_TYPE_MAP.put(MALL_ITEM_RECOMMEND_RECOMMEND_TYPE_ORDER, "全部商品排序值TOP");
    }

    public static final int MALL_ITEM_RECOMMEND_RECOMMEND_STATUS_INVALID = 0;//无效
    public static final int MALL_ITEM_RECOMMEND_RECOMMEND_STATUS_EFFECTIVE = 1;//有效

}
