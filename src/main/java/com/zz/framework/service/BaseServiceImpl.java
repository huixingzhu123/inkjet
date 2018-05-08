package com.zz.framework.service;

import com.zz.framework.domain.BaseEntity;
import com.zz.framework.repository.DefaultRepository;
import com.zz.framework.result.pagination.Pagination;
import com.zz.framework.result.pagination.PaginationSuccessDTO;
import com.zz.framework.util.SimpleSortBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * 服务层基类实现类
 *
 * @param <T>
 * @author yanjunhao
 * @date 2017年10月9日
 */
public class BaseServiceImpl<T> implements BaseService<T> {

    private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

    private final DefaultRepository<T, String> defaultRepository;

    public BaseServiceImpl(DefaultRepository<T, String> defaultRepository) {
        this.defaultRepository = defaultRepository;
    }


    @Override
    public T getBySystemid(String systemid) {
        return defaultRepository.getBySystemid(systemid);
    }

    @Override
    public PaginationSuccessDTO<T> getEntices(Pagination pagination, String sort) {
        //处理sort
        Sort orders = SimpleSortBuilder.generateSort(sort);
        Page<T> page = defaultRepository.findAll(new PageRequest(pagination.getPage() - 1, pagination.getPageSize(), orders));

        return new PaginationSuccessDTO(page);
    }

    @Override
    public List<T> getByFieldName(String fieldName, Object fieldValue) {
        return defaultRepository.getByFieldName(fieldName, fieldValue);
    }

    @Override
    public int getCountByFieldName(String fieldName, Object fieldValue) {
        return defaultRepository.getCountByFieldName(fieldName, fieldValue);
    }

    @Override
    public List<T> getByFieldName(String fieldName, Object fieldValue, int page, int size) {
        return defaultRepository.getByFieldName(fieldName, fieldValue, page, size);
    }

    @Override
    public List<T> getByFieldName(String fieldName, Object fieldValue, String orderField, Sort.Direction direction) {
        return defaultRepository.getByFieldName(fieldName, fieldValue, orderField, direction);
    }

    @Override
    public List<T> getByFieldName(String fieldName, Object fieldValue, int page, int size, String orderField, Sort.Direction direction) {
        return defaultRepository.getByFieldName(fieldName, fieldValue, page, size, orderField, direction);
    }

    @Override
    public void remove(T entity) {
        defaultRepository.delete(entity);
    }

    @Override
    public void remove(String id) {
        defaultRepository.delete(id);
    }

    @Override
    public void logicRemove(String... ids) {
        defaultRepository.logicRemove(ids);
    }

    @Override
    public T update(T entity) {
        return defaultRepository.save(entity);
    }

    @Override
    public T save(T entity) {
        return defaultRepository.save(entity);
    }

    @Override
    public List<T> batchSave(List<T> entityList) {
        return defaultRepository.save(entityList);
    }

    @Override
    public boolean isExist(String id) {
        return defaultRepository.exists(id);
    }

    @Override
    public boolean isExist(T entity) {
        BaseEntity baseEntity = (BaseEntity) entity;
        return defaultRepository.exists(baseEntity.getSystemid());
    }

    //@Override
    /*public List<T> queryForList(String nativeSql, Object[] args, Class<T> returnClazz) {
        return defaultRepository.queryForList(nativeSql, args, returnClazz);
    }*/

    //@Override
   /* public List<Map<String, Object>> queryForList(String nativeSql, Object[] args) {
        return defaultRepository.queryForList(nativeSql, args);
    }*/
}
