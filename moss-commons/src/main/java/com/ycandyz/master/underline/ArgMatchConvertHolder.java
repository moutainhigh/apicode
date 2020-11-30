package com.ycandyz.master.underline;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @description {@link ArgMatchConvert} 管理类
 * @author SanGang
 */
public class ArgMatchConvertHolder{

    private static final List<ArgMatchConvert<?>> ARG_MATCH_CONVERTS = new ArrayList<>();

    public static List<ArgMatchConvert<?>> getConverts(){
        return ARG_MATCH_CONVERTS;
    }

    public static void register(ArgMatchConvert<?> source){
        ARG_MATCH_CONVERTS.add(source);
    }

    public static void clear(){
        if(!CollectionUtils.isEmpty(ARG_MATCH_CONVERTS)){
            ARG_MATCH_CONVERTS.clear();
        }
    }
}