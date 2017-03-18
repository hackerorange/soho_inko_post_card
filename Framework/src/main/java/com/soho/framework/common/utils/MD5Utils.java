package com.soho.framework.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SuppressWarnings("StringBufferMayBeStringBuilder")
public class MD5Utils {
    public static String encryption(String plain) {
        String re_md5 = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plain.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (byte aB : b) {
                i = aB;
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            re_md5 = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return re_md5;
    }
}

