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
    public void batch(List<T> list) {
        this.getMapper().batchSave(list);
    }

    /**
     * 删除
     *
     * @param t 参数
     */
    @Override
    public void delete(T t) {
        this.getMapper().delete(t);
    }

    /**
     * 批量删除
     *
     * @param list 参数
     * @return 删除失败
     */
    @Override
    public List<DynamoDBMapper.FailedBatch> delete(List<T> list) {
        return this.getMapper().batchDelete(list);
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
        return this.getMapper().query(this.getTClass(), this.getQuery(keyCondition, attributeValueMap, filterExpression, asc));
    }

    /**
     * count
     *
     * @param keyCondition keyCondition
     * @param attributeValueMap attributeValueMap
     * @return count
     */
    @Override
    public int countByQuery(String keyCondition, Map<String, AttributeValue> attributeValueMap) {
        return this.getMapper().count(this.getTClass(), this.getQuery(keyCondition, attributeValueMap));
    }

    /**
     * count
     *
     * @param keyCondition keyCondition
     * @param attributeValueMap attributeValueMap
     * @param filterExpression filterExpression
     * @return count
     */
    @Override
    public int countByQuery(String keyCondition, Map<String, AttributeValue> attributeValueMap, String filterExpression) {
        return this.getMapper().count(this.getTClass(), this.getQuery(keyCondition, attributeValueMap, filterExpression));
    }

    private DynamoDBQueryExpression<T> getQuery(String keyCondition, Map<String, AttributeValue> attributeValueMap) {
        return this.getQuery(keyCondition, attributeValueMap, null);
    }

    private DynamoDBQueryExpression<T> getQuery(String keyCondition, Map<String, AttributeValue> attributeValueMap, String filterExpression) {
        return this.getQuery(keyCondition, attributeValueMap, filterExpression, true);
    }

    private DynamoDBQueryExpression<T> getQuery(String keyCondition, Map<String, AttributeValue> attributeValueMap, String filterExpression, final boolean asc) {
        final DynamoDBQueryExpression<T> query = new DynamoDBQueryExpression<T>();
        query.withKeyConditionExpression(keyCondition)
                .withExpressionAttributeValues(attributeValueMap);
        if (filterExpression != null && !filterExpression.isEmpty()) {
            query.withFilterExpression(filterExpression);
        }
        query.withScanIndexForward(asc);
        return query;
    }

    protected Class<T> getTClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
