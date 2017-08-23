package com.wenwen.sweet.util;

/**
 * @author yunxiangzyx
 * @date 16/3/17.
 */
public class VersionController {

    private static final long version = System.currentTimeMillis();

    public String toString() {
        return String.valueOf(version);
    }

}
