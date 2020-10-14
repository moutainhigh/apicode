package com.ycandyz.master.utils;

import com.ycandyz.master.utils.id.SnowflakeIdHelper;
import org.springframework.beans.factory.annotation.Value;

/**
 * <P>
 * 用途:生成数字id
 * </p>
 *
 * @author: Wang Yang
 * @create: 2020-10-12 21:01
 **/
public class IDGeneratorUtils {
    @Value("${id.workerId}")
    private static long workerId;

    @Value("${id.datacenterId}")
    private static long datacenterId;

    public static long getLongId(){
        final SnowflakeIdHelper snowflakeIdHelper = SnowflakeIdHelper.getInstance(workerId,datacenterId);
        long id = snowflakeIdHelper.nextId();
        return id;
    }
}
