package com.wenwen.sweet.util;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Created by tbj on 17/8/26.
 */
public class SweetBusinessException extends RuntimeException {

    public SweetBusinessException() {
        super();
    }

    public SweetBusinessException(String message) {
        super(message);
    }

    public SweetBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public SweetBusinessException(Throwable cause) {
        super(cause);
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        /**
         * 业务异常不需要堆栈信息， 重载该方法提高性能
         */
        return null;
    }
}
