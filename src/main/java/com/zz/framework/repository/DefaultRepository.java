package com.zz.framework.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 默认的jpa Repository
 *
 * @param <T>
 * @author yanjunhao
 * @date 2017.09.29
 */
@NoRepositoryBean
public interface DefaultRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
    /**
     * 根据主键获取实体
     *
     * @param id 主键
     * @return 实体
     */
    T getBySystemid(ID id);

    /**
     * 通过字段内容查询
     *
     * @param fieldName  字段名
     * @param fieldValue 字段内容
     * @return 实体列表
     */
    List<T> getByFieldName(String fieldName, Object fieldValue);

    /**
     * 通过字段内容查询数量
     *
     * @param fieldName  字段名
     * @param fieldValue 字段内容
     * @return 实体数量
     */
    int getCountByFieldName(String fieldName, Object fieldValue);

    /**
     * 逻辑删除
     *
     * @param ids id可变参
     */
    @Transactional
    void logicRemove(ID... ids);

    /**
     * 通过字段内容查询（分页）
     *
     * @param fieldName  字段名
     * @param fieldValue 字段内容
     * @param page       第几页，从1开始
     * @param size       每页大小
     * @return 实体列表
     */
    List<T> getByFieldName(String fieldName, Object fieldValue, int page, int size);

    /**
     * 通过字段内容查询（排序）
     *
     * @param fieldName  字段名
     * @param fieldValue 字段内容
     * @param orderField 排序字段
     * @param direction  排序类型 Sort.Direction 枚举 空则默认为Sort.Direction.ASC
     * @return 实体列表
     */
    List<T> getByFieldName(String fieldName, Object fieldValue, String orderField, Sort.Direction direction);

    /**
     * 通过字段内容查询（分页排序）
     *
     * @param fieldName  字段名
     * @param fieldValue 字段内容
     * @param page       第几页，从1开始
     * @param size       每页大小
     * @param orderField 排序字段
     * @param direction  排序类型 Sort.Direction 枚举 空则默认为Sort.Direction.ASC
     * @return 实体列表
     */
    List<T> getByFieldName(String fieldName, Object fieldValue, int page, int size, String orderField, Sort.Direction direction);

    /**
     * 通过原生sql查询实体列表
     *
     * @param nativeSql   sql语句
     * @param args        参数数组
     * @param returnClazz 实体类型
     * @return 实体列表
     */
    List<T> queryForList(String nativeSql, Object[] args, Class<T> returnClazz);

    /**
     * 通过原生sql查询实体列表（分页）
     *
     * @param nativeSql   sql语句
     * @param args        参数数组
     * @param returnClazz 实体类型
     * @param page 页数 从1开始
     * @param size 每页大小
     * @return 实体列表
     */
    List<T> queryForList(String nativeSql, Object[] args, Class<T> returnClazz, int page, int size);

    /**
     * 通过原生sql查询结果集，会损耗性能
     *
     * @param nativeSql sql语句
     * @param args      参数数组
     * @return 结果集的列表
     */
    List<Map<String, Object>> queryForList(String nativeSql, Object[] args);

    /**
     * 通过原生sql查询结果集，会损耗性能 （分页）
     *
     * @param nativeSql sql语句
     * @param args      参数数组
     * @param page      第几页 从1开始
     * @param size      每页大小
     * @return 结果集的列表
     */
    List<Map<String, Object>> queryForList(String nativeSql, Object[] args, int page, int size);

    /**
     * 通过原生sql执行更新操作
     * @param nativeSql sql语句
     * @param args 参数数组
     */
    void updateByNativeSql(String nativeSql, Object[] args);
}
