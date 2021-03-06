package com.ycandyz.master.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONException;
import cn.hutool.json.JSONObject;

/**
 * @author: WangYang
 * @Date: 2020/9/23 17:20
 * @Description:
 */
public class TokenUtil {

    /**
     * 验签token
     * @param token
     * @return
     */
    public static JSONObject verifyToken(String token,String secret) throws JSONException {

        Integer userId = 0;
        String decodeStr = Base64.decodeStr(token);
        JSONObject json = new JSONObject(decodeStr,true);
        String jtiStr = json.get("jti").toString();
        String iatStr = json.get("iat").toString();
        String zanbKey = SecureUtil.md5(iatStr + secret);
        json.remove("jti");

        String verifyStr = json.toString() + "key=" + zanbKey;
        String jtiResult = SecureUtil.md5(verifyStr);

        if(jtiStr.equals(jtiResult)){
//            String userIdJson = json.get("user_id").toString();
//            userId = Integer.parseInt(userIdJson);
            return json;
        }
        return null;
//        return userId;
    }

}
