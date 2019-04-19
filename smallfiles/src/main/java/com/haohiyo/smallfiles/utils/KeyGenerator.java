package com.haohiyo.smallfiles.utils;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.time.FastDateFormat;

/**
 * generate ID
 * @author zhenghongHYE
 *
 */
public class KeyGenerator {

    private static FastDateFormat fdf = FastDateFormat.getInstance("yyyyMMddHHmm");

    public static String genKey() {
        return fdf.format(new Date()) + "-" + UUID.randomUUID().toString();
    }
}
