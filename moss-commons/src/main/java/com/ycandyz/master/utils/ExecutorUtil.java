package com.ycandyz.master.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author sangang
 */
public class ExecutorUtil {

    //
    public static ExecutorService cachedPool = Executors.newCachedThreadPool();
    //
    public static ExecutorService fixedPool = Executors.newFixedThreadPool(50);

}
