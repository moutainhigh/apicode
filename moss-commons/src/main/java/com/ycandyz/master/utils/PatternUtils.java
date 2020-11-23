package com.ycandyz.master.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtils {

    //图片正则匹配
    public static String reg(String line) {
        Pattern pattern = Pattern.compile("https://(?!(\\.jpg|\\.png|\\.jpeg)).+?(\\.jpg|\\.png|\\.jpeg)");
        Matcher matcher = pattern.matcher(line);
        String url = "";
        if (matcher.find()) {
            url = matcher.group(0);
        }
        return url;
    }


    //富文本p标签
    public static String getpStr(String htmlStr) {
        if (htmlStr == null){
            return null;
        }
        StringBuilder sb = new StringBuilder();
        //获取p标签和内容
        String reg = "<p[^>]*>(?:(?!<\\/p>)[\\s\\S])*<\\/p>";
        Pattern pattern = Pattern.compile (reg);
        Matcher matcher = pattern.matcher (htmlStr);
        while (matcher.find ())
        {
            String result = matcher.group();
            if (StringUtils.isNotBlank(result)){
                result = result.replace("<p>","");
                result = result.replace("</p>","");
            }
            //过滤html标签
            sb.append(result);
        }
        return sb.toString();
    }

    //富文本img标签
    public static List<String> getImgStr(String htmlStr) {
        if (htmlStr == null){
            return null;
        }
        List<String> list = new ArrayList<>();
        String img = "";
        Pattern p_image;
        Matcher m_image;
        // String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            // 得到<img />数据
            img = m_image.group();
            // 匹配<img>中的src数据
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                list.add(m.group(1));
            }
        }
        return list;
    }
}
