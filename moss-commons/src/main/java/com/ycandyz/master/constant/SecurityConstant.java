package com.ycandyz.master.constant;

import cn.hutool.crypto.digest.DigestUtil;

/**
 * @author SanGang
 */
public interface SecurityConstant {

    /**
     * JWT存储权限前缀
     */
    String AUTHORITY_PREFIX = "ROLE_";

    /**
     * 后台管理client_id
     */
    String ADMIN_CLIENT_ID = "admin-app";

    /**
     * 前台商城client_id
     */
    String PORTAL_CLIENT_ID = "portal-app";

    /**
     * Redis缓存权限规则key
     */
    String RESOURCE_ROLES_MAP_KEY = "auth:resourceRolesMap";

    /**
     * 用户信息Http请求头
     */
    String USER_TOKEN_HEADER = "currentUser";

    /**
     * 认证接口路径匹配
     */
    String OAUTH_URL = "/oauth/";

    /**
     * author参数头
     */
    String ACCESS_TOKEN = "Authorization";

    /**
     * JWT令牌前缀
     */
    String JWT_TOKEN_PREFIX = "Bearer ";

    String ACCESS_PREFIX = "access:";
    /**
     * token分割
     */
    public static final String TOKEN_PREFIX = "sangang";
    /**
     * JWT签名加密key
     */
    public static final String JWT_SIGN_KEY = DigestUtil.md5Hex(TOKEN_PREFIX);

    /**
     * TOKEN_HEADER前缀
     */
    String JWT_TOKEN_HEADER = "header";

    /**
     * TOKEN参数名
     */
    String JWT_TOKEN = "x-auth-token";

    /**
     * author的token有效期
     */
    public static final Integer OAUTH_OFFSET_DAY = 1;
    /**
     * C端用户的token有效期
     */
    public static final Integer C_OFFSET_DAY = 7;
    /**
     * B端用户的token有效期
     */
    public static final Integer B_OFFSET_DAY = 30;

    /**
     * 系统固定不进行认证，直接放行的URL，供WebSecurityConfig、ResourceServerConfig公用
     */
    String[] PATTERN_URLS = {
            "/instances",
            "/actuator/**",
            "/druid/**",
            "/assets/**",
            "/webjars/**",
            "/configuration/ui",
            "/swagger-resources",
            "/configuration/security",
            "/swagger-ui.html",
            "/docs.html",
            "/doc.html",
            "/swagger-resources/**",
            "/v2/api-docs"
    };

}
