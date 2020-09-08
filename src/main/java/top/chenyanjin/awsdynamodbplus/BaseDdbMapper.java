/*
 * Copyright(c) 2020 chenyanjin.top, All Rights Reserved.
 */

package top.chenyanjin.awsdynamodbplus;

import java.util.List;

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
}
