/*
 * Copyright(c) 2020 chenyanjin.top, All Rights Reserved.
 */

package com.chenyanjin.awsdynamodbplus;

/**
 * Created by IntelliJ IDEA.
 * 同时有 hash key 和 sort key
 *
 * @author: chenchaopeng
 * Date: 2020/8/28
 */
public interface BaseHDdbMapper<T, H> extends BaseDdbMapper<T> {

    /**
     * 根据id获取
     * @param h hash key
     * @return t
     */
    T getById(H h);

}
