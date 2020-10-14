package com.ycandyz.master.constants;

public interface CommonConstant {

	/**
	 * 正常状态
	 */
	Integer STATUS_NORMAL = 0;

	/**
	 * 禁用状态
	 */
	Integer STATUS_DISABLE = -1;

	/**
	 * 删除标志
	 */
	Integer DEL_FLAG_1 = 1;

	/**
	 * 未删除
	 */
	Integer DEL_FLAG_0 = 0;

	/**
	 * 系统日志类型： 登录
	 */
	int LOG_TYPE_1 = 1;
	
	/**
	 * 系统日志类型： 操作
	 */
	int LOG_TYPE_2 = 2;

	/**
	 * 操作日志类型： 查询
	 */
	int OPERATE_TYPE_1 = 1;
	
	/**
	 * 操作日志类型： 添加
	 */
	int OPERATE_TYPE_2 = 2;
	
	/**
	 * 操作日志类型： 更新
	 */
	int OPERATE_TYPE_3 = 3;
	
	/**
	 * 操作日志类型： 删除
	 */
	int OPERATE_TYPE_4 = 4;
	
	/**
	 * 操作日志类型： 倒入
	 */
	int OPERATE_TYPE_5 = 5;
	
	/**
	 * 操作日志类型： 导出
	 */
	int OPERATE_TYPE_6 = 6;
	
	
	/** {@code 500 Server Error} (HTTP/1.0 - RFC 1945) */
    Integer SC_INTERNAL_SERVER_ERROR_500 = 500;
    /** {@code 200 OK} (HTTP/1.0 - RFC 1945) */
    Integer SC_OK_200 = 200;
    
    /**访问权限认证未通过 510*/
    Integer SC_JEECG_NO_AUTHZ=510;

    /** 登录用户拥有角色缓存KEY前缀 */
    String LOGIN_USER_CACHERULES_ROLE = "loginUser_cacheRules::Roles_";
    /** 登录用户拥有权限缓存KEY前缀 */
    String LOGIN_USER_CACHERULES_PERMISSION  = "loginUser_cacheRules::Permissions_";
    /** 登录用户令牌缓存KEY前缀 */
    int  TOKEN_EXPIRE_TIME  = 3600; //3600秒即是一小时
    
    String PREFIX_USER_TOKEN  = "PREFIX_USER_TOKEN_";

	String LASTER_SIGN_PHONE_USER_TOKEN = "LASTER_SIGN_USER_TOKEN";	//存储最新登录用户的token，用于下线旧的在线用户
	String SIGN_PHONE_USER = "phone@";	//IP端识别

	String DEFAULT_PWD="zhongben1314";	//默认密码
    
    /**
     *  0：一级菜单
     */
    Integer MENU_TYPE_0  = 0;
   /**
    *  1：子菜单 
    */
    Integer MENU_TYPE_1  = 1;
    /**
     *  2：按钮权限
     */
    Integer MENU_TYPE_2  = 2;
    
    /**通告对象类型（USER:指定用户，ALL:全体用户）*/
    String MSG_TYPE_UESR  = "USER";
    String MSG_TYPE_ALL  = "ALL";
    
    /**发布状态（0未发布，1已发布，2已撤销）*/
    String NO_SEND  = "0";
    String HAS_SEND  = "1";
    String HAS_CANCLE  = "2";
    
    /**阅读状态（0未读，1已读）*/
    String HAS_READ_FLAG  = "1";
    String NO_READ_FLAG  = "0";
    
    /**优先级（L低，M中，H高）*/
    String PRIORITY_L  = "L";
    String PRIORITY_M  = "M";
    String PRIORITY_H  = "H";
    
    /**
     * 短信模板方式  0 .登录模板、1.注册模板、2.忘记密码模板
     */
    String SMS_TPL_TYPE_0  = "0";
    String SMS_TPL_TYPE_1  = "1";
    String SMS_TPL_TYPE_2  = "2";
    
    /**
     * 状态(0无效1有效)
     */
    String STATUS_0 = "0";
    String STATUS_1 = "1";
    
    /**
     * 同步工作流引擎1同步0不同步
     */
    String ACT_SYNC_0 = "0";
    String ACT_SYNC_1 = "1";
    
    /**
     * 消息类型1:通知公告2:系统消息
     */
    String MSG_CATEGORY_1 = "1";
    String MSG_CATEGORY_2 = "2";
    
    /**
     * 是否配置菜单的数据权限 1是0否
     */
    Integer RULE_FLAG_0 = 0;
    Integer RULE_FLAG_1 = 1;

    /**
     * 是否用户已被冻结 1(解冻)正常 2冻结
     */
    Integer USER_UNFREEZE = 1;
    Integer USER_FREEZE = 2;
    
    /**字典翻译文本后缀*/
    String DICT_TEXT_SUFFIX = "_dictText";


	//短信类型

	/**
	 * 注册
	 */
	String SMS_EVENT_REGISTER = "register";

	/**
	 * 登陆
	 */
	String SMS_EVENT_LOGIN = "login";

	/**
	 * 忘记密码
	 */
	String SMS_EVENT_FORGET = "forget";

	/**
	 * 修改密码
	 */
	String SMS_EVENT_CHANGE_PWD = "changePwd";

	/**
	 * 修改支付密码
	 */
	String SMS_EVENT_CHANGE_PAY_PWD = "changePayPwd";

	/**
	 * 更换手机号码
	 */
	String SMS_EVENT_CHANGEPHONE = "changePhone";

	/**
	 * 验证手机号码
	 */
	String SMS_EVENT_VERIFYPHONE = "verifyPhone";

	/**
	 * 退店
	 */
	String SMS_EVENT_RETREATSHOP = "retreatShop";

	/**
	 * 三方登录
	 */
	String SMS_EVENT_BINDTHIRD = "bindThird";

	/**
	 * 首页新增redis标识
	 */

	String ORDER_NEW_ADD = "ORDER_NEW_ADD:";

	/**
	 * 小程序的accesstoken
	 */
	String MINI_ACCESS_TOKEN = "accessToken";
}
