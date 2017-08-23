package com.wenwen.sweet.commons;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @param <T>
 * @author yunxiang.zhang
 * @date 2016年1月30日
 */
public class JsonWrapper<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int LOGIN_FAIL = -2;
    public static final int CODE_SUCC = 0;
    public static final int CODE_FAIL = -1;
    public static final Object EMPTY_OBJECT = new Object();

    private static final String CODE_KEY = "status_code";
    private static final String REASON_KEY = "status_reason";

    private Map<String, Object> status = new HashMap<String, Object>();
    private T result; // 结果a

    public JsonWrapper() {
        status.put(CODE_KEY, CODE_SUCC);
        status.put(REASON_KEY, "success");
    }

    public JsonWrapper(int statusCode, String failReason) {
        status.put(CODE_KEY, statusCode);
        status.put(REASON_KEY, failReason);
    }

    public JsonWrapper(T result) {
        this();
        setResult(result);
    }

    public void setStatus(int statusCode, String statusReason) {
        status.put(CODE_KEY, statusCode);
        status.put(REASON_KEY, statusReason);
    }

    public void setFailStatus(String statusReason) {
        status.put(CODE_KEY, CODE_FAIL);
        status.put(REASON_KEY, statusReason);
    }

    public Map<String, Object> getStatus() {
        return status;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "JsonWrapper{" +
                "status=" + status +
                ", result=" + result +
                '}';
    }

    public static JsonWrapper<Object> buildSuccessResult() {
        return new JsonWrapper<Object>(EMPTY_OBJECT);
    }

    public static <T> JsonWrapper<T> buildSuccessResult(T t) {
        return new JsonWrapper<T>(t);
    }

    public static JsonWrapper<Object> buildFailedResult(String failReason) {
        return new JsonWrapper<Object>(CODE_FAIL, failReason);
    }
}
