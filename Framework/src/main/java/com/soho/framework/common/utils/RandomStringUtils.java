package com.soho.framework.common.utils;

import org.springframework.util.StringUtils;

import java.util.Random;




/**
 * Created by ZhongChongtao on 2017/2/17.
 */
@SuppressWarnings("StringBufferMayBeStringBuilder")
public class RandomStringUtils {
    /**
     * 随机字符串生成器
     *
     * @param bit 要生成的字符串的位数
     * @param str 字符串的组成字符
     * @return 生成的随机字符串
     */
    public static String random(int bit, String str) {
        if (StringUtils.isEmpty(str)) {
            System.out.println("必须提供组成字符串");
            return "";
        }
        if (bit <= 0) {
            System.out.println("生成的位数必须大于0");
            return "";
        }
        Random random = new Random();
        int length = str.length();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < bit; i++) {
            stringBuffer.append(str.charAt(random.nextInt(length)));
        }
        return stringBuffer.toString();
    }
}

//~ Formatted by Jindent --- http://www.jindent.com
