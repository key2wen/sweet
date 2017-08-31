package com.wenwen.sweet.convert;

/**
 * Created by tbj on 17/8/26.
 */
public interface BaseConvert<M, V> {

    V toVO(M m);

    M toModel(V v);

}
