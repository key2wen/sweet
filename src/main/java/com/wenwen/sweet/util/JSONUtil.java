package com.wenwen.sweet.util;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * JSONUtil.java
 *
 * @version 1.0
 * @date 2016-03-19
 * @author ls
 *
 * 描述：json转换工具类
 */
public class JSONUtil {

    /**
     * Fast-JSONObject 转成 Map对象
     * @param object
     * @return
     */
    public static Map<String,String> jsonToMap(JSONObject object){

        Map<String, String> result = Maps.newHashMap();
        if(object == null)
            return result;

        for(String key : object.keySet())
            result.put(key, object.getString(key));

        return result;
    }
}
