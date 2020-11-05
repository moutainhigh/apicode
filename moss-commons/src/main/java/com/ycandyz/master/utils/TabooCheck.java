package com.ycandyz.master.utils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TabooCheck {
	/** 敏感词集合
     * {法={isEnd=0, 轮={isEnd=1}}, 中={isEnd=0, 国={isEnd=0, 人={isEnd=1}, 男={isEnd=0, 人={isEnd=1}}}}}
     * */
    private static HashMap keysMap = new HashMap();

    /**
     * 添加敏感词
     * @param keywords
     */
    public static void addKeywords(List<String> keywords) {
        for (int i = 0; i < keywords.size(); i++) {
            String key = keywords.get(i).trim();
            HashMap nowhash = keysMap;//初始从最外层遍历
            for (int j = 0; j < key.length(); j++) {
                char word = key.charAt(j);
                Object wordMap = nowhash.get(word);
                if (wordMap != null) {
                    nowhash = (HashMap) wordMap;
                } else {
                    HashMap<String, String> newWordHash = new HashMap<String, String>();
                    newWordHash.put("isEnd", "0");
                    nowhash.put(word, newWordHash);
                    nowhash = newWordHash;
                }
                if (j == key.length() - 1) {
                    nowhash.put("isEnd", "1");
                }
            }
        }
    }

    /**
     * 检查一个字符串从begin位置起开始是否有keyword符合，
     * 如果没有，则返回0
     * 如果有符合的keyword值，继续遍历，直至遇到isEnd = 1，返回匹配的keyword的长度，
     */
    private static int checkKeyWords(String txt, int begin) {
        HashMap nowhash = keysMap;
        int res = 0;
        for (int i = begin; i < txt.length(); i++) {
            char word = txt.charAt(i);
            Object wordMap = nowhash.get(word);//得到该字符对应的HashMap
            if (wordMap == null) {
                return 0;//如果该字符没有对应的HashMap，return 0
            }

            res++;//如果该字符对应的HashMap不为null，说明匹配到了一个字符，+1
            nowhash = (HashMap) wordMap;//将遍历的HashMap指向该字符对应的HashMap
            if (((String) nowhash.get("isEnd")).equals("1")) {//如果该字符为敏感词的结束字符，直接返回
                return res;
            } else {
                continue;
            }
        }
        return res;
    }

    /**
     * 判断txt中是否有关键字
     */
    public static boolean isContentKeyWords(String txt) {
        for (int i = 0; i < txt.length(); i++) {
            int len = checkKeyWords(txt, i);
            if (len > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 返回txt中关键字的列表
     */
    public static List<String> getTxtKeyWords(String txt) {
        List<String> list = new ArrayList<String>();
        int l = txt.length();
        for (int i = 0; i < l;) {
            int len = checkKeyWords(txt, i);
            if (len > 0) {
                String tt = txt.substring(i, i + len);
                list.add(tt);
                i += len;
            } else {
                i++;
            }
        }
        return list;
    }

    /**
     * 初始化敏感词列表
     * */

    public static List check(List<String> allKeys,String txt){
        addKeywords(allKeys);
        List<String> txtKeyWords = getTxtKeyWords(txt);
        return txtKeyWords;
    }

//    public static void main(String[] args) throws IOException {
//        Ws filter = new Ws();
//        filter.initfiltercode();
////        String txt = "父亲在紧挨着最后一间石达赖棉瓦房的打砸抢一块空地上，清除杂草，dalan平整土地，然后买来石棉瓦，一点一点，搭建起了一个临时的家。当天晚上，我们一家便住进了这间小小的石棉瓦房。谁知半夜，大雨突然倾盆而下，雨水从屋檐处顺着石棉瓦不停地往下流，很快就浸透了地面的泥土。第二天，父亲重新买来材料，改进了石棉瓦房，我们一家总算在城里有了一个可以遮风挡雨的家。\\n\" +\n" +
////                "                \"\\n\" +\n" +
////                "                \"    那是改自由西藏学生是运动啊革开放的初期，城市的建设大dl幕毛泽东刚刚拉开不久，于是便有了像我们这DZ样进城讨生活的乡下人。那时候拖粉碎四人帮拉机的生意也是主要来自于给建筑工程运送一些砂石之类的建筑材料。慢慢的，父亲从刚开始的靠别人介绍生意，到自己有了一些渠道。\\n\" +\n" +
////                "                \"\\n\" +\n" +
////                "                \"    有一次，父亲带着我一AY起出dizhi去运货。那是给一家雇主习近平运送一车装修用打倒江主席的砂子。运完给钱的死全抵制共产主义家时候，雇主坚持要把零头五块钱抹掉。那时候，五块钱的购买了不像现在这般小，父亲也是坚持不肯抹去。一番争议之后，雇主不耐烦地从口袋里掏出几张纸币甩在父亲手机，扬长而去，虎骑父亲数了数，那五块钱成人色情，雇主最终没有给。二十年来，这件事情一直深深地印在了我的记忆里，因为从那时我知道了，这条城市里讨生活的路，，并没有像我们进城来时的路那样宽敞而又平坦。\\n\" +\n" +
////                "                \"\\n\" +\n" +
////                "                \"    那时候，几间石找小姐棉瓦房dz与周围近在咫尺的啊找小姐姐高楼大厦形成了强烈的对比，似乎在告诉打倒江主席啊我们，我们不属于这里，" ;
//        String txt = "那是改自司机撒是全哦的啊是我";
//        boolean boo = filter.isContentKeyWords(txt);
//        System.out.println(boo);
//        List<String> set = filter.getTxtKeyWords(txt);
//        System.out.println("包含的敏感词如下：" + set);
//    }
}
