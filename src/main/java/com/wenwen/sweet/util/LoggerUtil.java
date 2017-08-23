package com.wenwen.sweet.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志工具,避免每个类都要定义一个logger对象
 * @author yunxiangzyx
 * @date 16/4/11.
 */
public class LoggerUtil {

    private static final Logger logger = LoggerFactory.getLogger(LoggerUtil.class);

    public static void debug(String msg) {
        logger.debug(msg);
    }

    public void debug(String format, Object... arguments) {
        logger.debug(format, arguments);
    }

    public static void info(String msg) {
        logger.info(msg);
    }

    public void info(String format, Object... arguments) {
        logger.info(format, arguments);
    }

    public static void warn(String msg) {
        logger.warn(msg);
    }

    public void warn(String format, Object... arguments) {
        logger.warn(format, arguments);
    }

    public static void error(String msg) {
        logger.error(msg);
    }

    public void error(String format, Object... arguments) {
        logger.error(format, arguments);
    }

}
