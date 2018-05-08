package com.zz.framework.service;

import com.zz.framework.result.pagination.Pagination;
import com.zz.framework.result.pagination.PaginationSuccessDTO;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * 服务基类
 *
 * @param <T>
 * @author yanjunhao
 * @date 2017.10.09
 */
public interface BaseService<T> {

    /**
     * 根据主键获取实体
     *
     * @param systemid 主键
     * @return 实体
     */
    T getBySystemid(String systemid);

    /**
     * 当page和pageSize为空-默认按主键降序取10条
     *
     * @param pagination     分页对象
     * @param sort     排序 field1-[d/a],field2-[d/a] 字段-d,字段-a d降序a升序
     * @return 分页对象
     */
    PaginationSuccessDTO<T> getEntices(Pagination pagination, String sort);

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
     * 删除实体
     *
     * @param entity 实体
     */
    void remove(T entity);

    /**
     * 删除实体
     *
     * @param id 实体主键
     */
    void remove(String id);


    /**
     * 逻辑删除，将Deleteflag标记为1
     *
     * @param ids 实体主键
     */
    void logicRemove(String... ids);

    /**
     * 更新实体
     * @param entity 实体
     * @return 更新后的实体
     */
    T update(T entity);

    /**
     * 保存
     *
     * @param entity 实体
     * @return 保存后的实体
     */
    T save(T entity);

    /**
     * 批量保存
     * @param entityList
     * @return
     */
    List<T> batchSave(List<T> entityList);

    /**
     * 实体是否存在
     *
     * @param id 实体主键
     * @return 布尔值
     */
    boolean isExist(String id);

    /**
     * 实体是否存在
     *
     * @param entity 实体
     * @return 布尔值
     */
    boolean isExist(T entity);

    /**
     * 通过原生sql查询实体列表（避免在控制层中编写sql，所以服务层不提供此方法，请在服务层中通过注入的Repository进行调用）
     *
     * @param nativeSql   sql语句
     * @param args        参数数组
     * @param returnClazz 实体类型
     * @return 实体列表
     */
    //List<T> queryForList(String nativeSql, Object[] args, Class<T> returnClazz);

    /**
     * 通过原生sql查询结果集，会损耗性能（避免在控制层中编写sql，所以服务层不提供此方法，请在服务层中通过注入的Repository进行调用）
     *
     * @param nativeSql sql语句
     * @param args      参数数组
     * @return 结果集的列表
     */
    //List<Map<String, Object>> queryForList(String nativeSql, Object[] args);

}
