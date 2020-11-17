package com.ycandyz.master.handler;

import com.google.common.collect.Maps;
import com.ycandyz.master.abstracts.AbstractHandler;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class HandlerContext {
    private static Map<Integer, AbstractHandler> handlerMap = Maps.newHashMap();


    public static AbstractHandler getHandler(Integer type){
        return handlerMap.get(type);
    }

    public static void putHandler(Integer type,AbstractHandler handler){
        handlerMap.put(type,handler);
    }

}
