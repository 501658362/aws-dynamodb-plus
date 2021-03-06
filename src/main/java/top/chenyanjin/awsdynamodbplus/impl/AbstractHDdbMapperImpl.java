/*
 * Copyright(c) 2020 chenyanjin.top, All Rights Reserved.
 */

package top.chenyanjin.awsdynamodbplus.impl;

import top.chenyanjin.awsdynamodbplus.BaseHDdbMapper;

import java.lang.reflect.ParameterizedType;

/**
 * Created by IntelliJ IDEA.
 * 同时拥有 hash key 和 sort key
 *
 * @author: chenchaopeng
 * Date: 2020/8/28
 */
public abstract class AbstractHDdbMapperImpl<T, H> extends AbstractDdbMapperImpl<T> implements BaseHDdbMapper<T, H> {

    /**
     * 根据id获取
     *
     * @param h hashKey
     * @return 实体
     */
    @Override
    public T getById(final H h) {
        return (T) this.getMapper().load(this.getTClass(), h);
    }

}
