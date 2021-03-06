/*
 * Copyright(c) 2020 chenyanjin.top, All Rights Reserved.
 */

package top.chenyanjin.awsdynamodbplus.impl;

import top.chenyanjin.awsdynamodbplus.BaseHSDdbMapper;

import java.lang.reflect.ParameterizedType;

/**
 * Created by IntelliJ IDEA.
 * 同时拥有 hash key 和 sort key
 *
 * @author: chenchaopeng
 * Date: 2020/8/28
 */
public abstract class AbstractHSDdbMapperImpl<T, H, S> extends AbstractDdbMapperImpl<T> implements BaseHSDdbMapper<T, H, S> {

    /**
     * 根据id获取
     *
     * @param h hashKey
     * @param s sortKey
     * @return 实体
     */
    @Override
    public T getById(final H h, final S s) {
        return (T) this.getMapper().load(super.getTClass(), h, s);
    }

}
