package com.ycandyz.master.wechat.miniprogram;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;

/**
 * 微信小程序工具类
 */
@Slf4j
public class MiniProgramService {

    /**
     * 获取小程序的accessToken
     * @param accessTokenUrl 小程序请求url
     * @param miniAppId 小程序appid
     * @param miniAppSecret 小程序secret
     * @return
     */
    public static String getMiniAccessToken(String accessTokenUrl, String miniAppId, String miniAppSecret){
        String accessJSON = HttpUtil.get(accessTokenUrl.replace("MINI_APP_ID",miniAppId).replace("MINI_APP_SECRET",miniAppSecret));
        JSONObject jsonObject = JSONObject.parseObject(accessJSON);
        String accesstoken = jsonObject.getString("access_token");
        return accesstoken;
    }

    /**
     * 获取小程序二维码的inputstream流
     * @param url   小程序二维码接口url
     * @param accessToken   小程序的accessToken
     * @param miniPageUrl   小程序页面的url
     * @param miniPageParam 小程序页面需要传的参数
     * @return
     */
    public static InputStream wxMiniQrCode(String url, String accessToken, String miniPageUrl, String miniPageParam){
        //获取小程序指定页面二维码
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("scene", miniPageParam);
        jsonObject.put("page",miniPageUrl);
        jsonObject.put("width","50");
        jsonObject.put("auto_color", false);
        JSONObject line_color = new JSONObject();
        line_color.put("r", 0);
        line_color.put("b", 0);
        line_color.put("g", 0);
        jsonObject.put("line_color", line_color);
        log.info("调用生成微信URL接口传参:" + jsonObject.toString());
//                        HttpSend.sendPostToBuffer(url,jsonObject,qrPath);
        HttpRequest httpRequest = HttpUtil.createPost(url.replace("ACCESS_TOKEN",accessToken));
        httpRequest.body(jsonObject.toJSONString());
        httpRequest.contentType("application/json;charset=utf-8");
        HttpResponse httpResponse = httpRequest.execute();
        InputStream inputStream = httpResponse.bodyStream();
        return inputStream;
    }
}
