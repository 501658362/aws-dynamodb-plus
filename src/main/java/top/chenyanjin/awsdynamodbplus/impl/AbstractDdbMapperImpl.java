/*
 * Copyright(c) 2020 chenyanjin.top, All Rights Reserved.
 */

package top.chenyanjin.awsdynamodbplus.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import top.chenyanjin.awsdynamodbplus.BaseDdbMapper;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

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

    /**
     * 批量保存
     *
     * @param list list
     */
    @Override
    public void batchSave(List<T> list) {
        this.getMapper().batchSave(list);
    }

    /**
     * 查询
     *
     * @param keyCondition      keyCondition
     * @param attributeValueMap attributeValueMap
     * @return list
     */
    @Override
    public List<T> query(final String keyCondition, final Map<String, AttributeValue> attributeValueMap) {
        return this.query(keyCondition, attributeValueMap, null, false);
    }

    /**
     * 查询
     *
     * @param keyCondition      keyCondition
     * @param attributeValueMap attributeValueMap
     * @param asc               asc
     * @return list
     */
    @Override
    public List<T> query(final String keyCondition, final Map<String, AttributeValue> attributeValueMap, final boolean asc) {
        return this.query(keyCondition, attributeValueMap, null, asc);
    }

    /**
     * 查询
     *
     * @param keyCondition      keyCondition
     * @param attributeValueMap attributeValueMap
     * @param filterExpression  filterExpression
     * @param asc               asc
     * @return list
     */
    @Override
    public List<T> query(final String keyCondition, final Map<String, AttributeValue> attributeValueMap, final String filterExpression, final boolean asc) {
        final DynamoDBQueryExpression<T> query = new DynamoDBQueryExpression<T>();
        query.withKeyConditionExpression(keyCondition)
                .withExpressionAttributeValues(attributeValueMap)
                .withScanIndexForward(false);
        if(filterExpression != null && !filterExpression.isEmpty()){
            query.withFilterExpression(filterExpression);
        }
        return this.getMapper().query(this.getTClass(), query);
    }

    protected Class<T> getTClass() {
        final Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return clazz;
    }
}
