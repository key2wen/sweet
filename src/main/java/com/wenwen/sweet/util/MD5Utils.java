package com.wenwen.sweet.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

/**
 * MD5Utils.java
 *
 * @version 1.0
 * @date 2016-01-30
 * @author ls
 *
 * 描述：MD5工具类
 */
public class MD5Utils {

    private static Logger log = LoggerFactory.getLogger(MD5Utils.class);

    private static MessageDigest md5 = null;

    static {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            log.error("init md5 error! {}", e.getMessage());
        }
    }

    /**
     * 用于获取一个String的md5值
     * @param str
     * @return
     */
    public static String md5(String str) {
        byte[] bs = md5.digest(str.getBytes());
        StringBuilder sb = new StringBuilder(40);
        for(byte x:bs) {
            if((x & 0xff)>>4 == 0) {
                sb.append("0").append(Integer.toHexString(x & 0xff));
            } else {
                sb.append(Integer.toHexString(x & 0xff));
            }
        }
        return sb.toString();
//        return sb.toString().toUpperCase();
    }

    public static void main(String[] args) {
        System.out.println(md5("t8njVjHs"));
    }
}
