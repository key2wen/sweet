package com.wenwen.sweet.util;


import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by yunxiangzyx on 16/2/5.
 */
public class StringConverter implements Converter<String, Integer> {
    @Override
    public Integer convert(String source) {
        if (StringUtils.isBlank(source)) {
            return 0;
        }
        return Integer.valueOf(source);
    }
}
