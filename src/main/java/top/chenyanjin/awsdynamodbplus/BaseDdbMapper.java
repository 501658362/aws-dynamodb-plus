/*
 * Copyright(c) 2020 chenyanjin.top, All Rights Reserved.
 */

package top.chenyanjin.awsdynamodbplus;

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
    void batchSave(List<T> list);


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
}
