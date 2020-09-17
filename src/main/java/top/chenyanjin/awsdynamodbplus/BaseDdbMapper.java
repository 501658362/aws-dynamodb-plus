/*
 * Copyright(c) 2020 chenyanjin.top, All Rights Reserved.
 */

package top.chenyanjin.awsdynamodbplus;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: chenchaopeng
 * Date: 2020/8/28
 */
public interface BaseDdbMapper<T> {

    /**
     * 保存
     * @param t 参数
     */
    void save(T t);

    /**
     * 选择性保存 忽略 null 值
     * @param t 参数
     */
    void saveSelective(T t);

    /**
     * 批量保存
     * @param list list
     */
    void batch(List<T> list);

    /**
     * 删除
     * @param t 参数
     */
    void delete(T t);

    /**
     * 批量删除
     * @param list 参数
     * @return 删除失败
     */
    List<DynamoDBMapper.FailedBatch> delete(List<T> list);


    /**
     * 查询
     * @param keyCondition keyCondition
     * @param attributeValueMap attributeValueMap
     * @return list
     */
    List<T> query(String keyCondition, Map<String, AttributeValue> attributeValueMap);

    /**
     * 查询
     * @param keyCondition keyCondition
     * @param attributeValueMap attributeValueMap
     * @param asc asc
     * @return list
     */
    List<T> query(String keyCondition, Map<String, AttributeValue> attributeValueMap, boolean asc);

    /**
     * 查询
     * @param keyCondition keyCondition
     * @param attributeValueMap attributeValueMap
     * @param filterExpression filterExpression
     * @param asc asc
     * @return list
     */
    List<T> query(String keyCondition, Map<String, AttributeValue> attributeValueMap, String filterExpression, boolean asc);

    /**
     * count
     *
     * @param keyCondition keyCondition
     * @param attributeValueMap attributeValueMap
     * @return count
     */
    int countByQuery(String keyCondition, Map<String, AttributeValue> attributeValueMap);

    /**
     * count
     *
     * @param keyCondition keyCondition
     * @param attributeValueMap attributeValueMap
     * @param filterExpression filterExpression
     * @return count
     */
    int countByQuery(String keyCondition, Map<String, AttributeValue> attributeValueMap, String filterExpression);

}
