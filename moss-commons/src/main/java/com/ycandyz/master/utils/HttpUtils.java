package com.ycandyz.master.utils;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import io.swagger.models.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * @author: WangYang
 * @Date: 2020/11/1 10:59
 * @Description:
 */
public class HttpUtils {


    /**
     * 将URL的参数和body参数合并
     * @author show
     * @date 14:24 2019/5/29
     * @param request
     */
    public static SortedMap<String, Object> getAllParams(HttpServletRequest request) throws IOException {

        SortedMap<String, Object> result = new TreeMap<>();
        //获取URL上的参数
        if (HttpMethod.GET.name().equals(request.getMethod())) {
            Map<String, String> urlParams = getUrlParams(request);
            for (Map.Entry entry : urlParams.entrySet()) {
                result.put((String) entry.getKey(), entry.getValue());
            }
        }
        Map<String, String> allRequestParam = new HashMap<>(16);
        // get请求不需要拿body参数
        if (!HttpMethod.GET.name().equals(request.getMethod())) {
            allRequestParam = getAllRequestParam(request);
        }
        //将URL的参数和body参数进行合并
        if (allRequestParam != null) {
            for (Map.Entry entry : allRequestParam.entrySet()) {
                result.put((String) entry.getKey(), entry.getValue());
            }
        }
        return result;
    }

    /**
     * 获取 Body 参数
     * @author show
     * @date 15:04 2019/5/30
     * @param request
     */
    public static Map<String, String> getAllRequestParam(final HttpServletRequest request) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String str = "";
        StringBuilder wholeStr = new StringBuilder();
        //一行一行的读取body体里面的内容；
        while ((str = reader.readLine()) != null) {
            wholeStr.append(str);
        }
        //转化成json对象
        return JSONObject.parseObject(wholeStr.toString(),LinkedHashMap.class,Feature.OrderedField);
    }

    /**
     * 将URL请求参数转换成Map
     * @author show
     * @param request
     */
    public static Map<String, String> getUrlParams(HttpServletRequest request) {

        String param = "";
        try {
            param = URLDecoder.decode(request.getQueryString(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Map<String, String> result = new HashMap<>(16);
        String[] params = param.split("&");
        for (String s : params) {
            int index = s.indexOf("=");
            result.put(s.substring(0, index), s.substring(index + 1));
        }
        return result;
    }

}
