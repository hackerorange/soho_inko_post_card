package com.soho.framework.common.utils;

import org.apache.commons.lang.time.DateUtils;

import java.util.Date;

/**
 * Created by ZhongChongtao on 2017/2/21.
 */
public class DateTimeUtils extends DateUtils {
    public static Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }
}

