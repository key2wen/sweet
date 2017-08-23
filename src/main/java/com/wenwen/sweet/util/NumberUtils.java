package com.wenwen.sweet.util;

import org.apache.commons.lang.StringUtils;

/**
 * NumberUtils.java
 *
 * @version 1.0
 * @date 2016-02-02
 * @author ls
 *
 * 描述：数字相关工具类
 */
public class NumberUtils {

    /**
     * 如果为Null则取默认值
     */
    public static Integer defaultIfNull(Integer value, Integer defaultValue) {

        return value == null ? defaultValue : value;
    }

    /**
     * 如果是正数则返回true
     */
    public static boolean isPositive(Number value) {
        return value != null && value.doubleValue() > 0;
    }

    /**
     * 是否在数组里面
     * @param number
     * @param args
     * @return
     */
    public static boolean isInArray(Integer number, Integer ... args) {

        if(number == null)
            return false;

        for( Integer i : args) {
            if( i== null)
                continue;
            if(number.intValue() == i.intValue())
                return true;
        }
        return false;
    }

    public static Integer parseInt(String str, Integer defautlValue) {
        if (StringUtils.isBlank(str))
            return defautlValue;
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return defautlValue;
        }
    }
}
