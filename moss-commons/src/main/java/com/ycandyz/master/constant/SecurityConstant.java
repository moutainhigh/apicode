package com.ycandyz.master.constant;

import cn.hutool.crypto.digest.DigestUtil;

/**
 * @author sangang
 */
public final class SecurityConstant {
	
	private SecurityConstant() {}
    /**
     * token分割
     */
    public static final String TOKEN_PREFIX = "sangang";
    /**
     * JWT签名加密key
     */
    public static final String JWT_SIGN_KEY = DigestUtil.md5Hex(TOKEN_PREFIX);
    /**
     * token参数头
     */
    public static final String OAUTH_TOKEN = "oauthToken";
    /**
     * token参数头
     */
    public static final String TOKEN = "token";
    /**
     * author参数头
     */
    public static final String AUTHORITIES = "authorities";
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

}
