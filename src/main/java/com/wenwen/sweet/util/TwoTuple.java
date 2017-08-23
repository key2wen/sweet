package com.wenwen.sweet.util;

/**
 * TwoTuple.java
 *
 * @version 1.0
 * @date 2016-03-19
 * @author ls
 *
 * 描述：二元组工具类，当方法返回两个参数时可以使用
 */
public class TwoTuple<A, B> {

    public final A first;

    public final B second;

    public TwoTuple(A first, B second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{first:");
        if (first == null)
            sb.append("null");
        else
            sb.append(first.toString());
        sb.append(" , ");
        if (second == null)
            sb.append("null");
        else
            sb.append(second.toString());
        sb.append("}");
        return sb.toString();
    }
}
