/*
 * Copyright(c) chenyanjin.top, All Rights Reserved.
 */

package com.chenyanjin.awsdynamodbplus.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.chenyanjin.awsdynamodbplus.BaseDdbMapper;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: chenchaopeng
 * Date: 2020/8/28
 */
public abstract class AbstractDdbMapperImpl<T> implements BaseDdbMapper<T> {

    /**
     * 由业务类实现
     *
     * @return DynamoDBMapper
     */
    protected abstract DynamoDBMapper getMapper();

    /**
     * 保存
     *
     * @param t 参数
     */
    @Override
    public void save(final T t) {
        this.getMapper().save(t);
    }

    /**
     * 选择性保存
     *
     * @param t 参数
     */
    @Override
    public void saveSelective(final T t) {
        this.getMapper().save(t, DynamoDBMapperConfig.builder().withSaveBehavior(DynamoDBMapperConfig
                .SaveBehavior.UPDATE_SKIP_NULL_ATTRIBUTES).build());
    }

}
