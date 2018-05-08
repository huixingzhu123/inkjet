package com.zz.framework.repository;

import com.zz.framework.security.SpringSecurityAuditorAware;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 默认的jpa Repository
 *
 * @param <T>
 * @author yanjunhao
 * @date 2017.09.29
 */
public class DefaultRepositoryImpl<T, ID extends Serializable>
        extends SimpleJpaRepository<T, ID> implements DefaultRepository<T, ID> {

    private Log logger = LogFactory.getLog(DefaultRepositoryImpl.class);

    private final EntityManager entityManager;

    public DefaultRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        // Keep the EntityManager around to used from the newly introduced methods.
        this.entityManager = entityManager;
    }

    @Override
    public T getBySystemid(ID id) {
        return this.findOne(id);
    }

    @Override
    public List<T> getByFieldName(String fieldName, Object fieldValue) {
        return this.findAll(getSpec(fieldName, fieldValue));
    }


    @Override
    public int getCountByFieldName(String fieldName, Object fieldValue) {
        Long count = this.count(getSpec(fieldName, fieldValue));
        return count.intValue();
    }

    private Specification<T> getSpec(String fieldName, Object fieldValue) {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(fieldName), fieldValue);
            }
        };
    }

    @Override
    public void logicRemove(ID... ids) {
        if (ids.length == 0) {
            return;
        }
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        //构建更新体
        CriteriaUpdate<T> update = builder.createCriteriaUpdate(this.getDomainClass());
        //from
        Root<T> root = update.from(this.getDomainClass());
        //表字段
        Path<String> deleteflag = root.get("deleteflag");
        Path<String> systemid = root.get("systemid");
        //审批字段赋值，因为实体不一定继承审批类，所以捕获一下异常
        try {
            Path<Date> lastUpdatedTime = root.get("lastUpdatedTime");
            Path<String> lastUpdatedBy = root.get("lastUpdatedBy");

            //实体审计功能类
            SpringSecurityAuditorAware auditorAware = new SpringSecurityAuditorAware();
            //设置最后更新人
            update.set(lastUpdatedBy, auditorAware.getCurrentAuditor());
            //设置最后更新时间
            update.set(lastUpdatedTime, auditorAware.getNow().getTime());
        } catch (IllegalArgumentException e) {
            logger.debug("不包含审计字段。");
        }

        //设置删除标志为1
        update.set(deleteflag, "1");

        //设置where条件
        Predicate predicate = systemid.in((Object[]) ids);
        update.where(predicate);

        //执行更新
        entityManager.createQuery(update).executeUpdate();
    }

    @Override
    public List<T> getByFieldName(String fieldName, Object fieldValue, int page, int size) {
        Page<T> pageList = this.findAll(getSpec(fieldName, fieldValue), new PageRequest(page - 1, size));
        List<T> entityList = new ArrayList<>();
        pageList.forEach((T entity) -> {
            entityList.add(entity);
        });
        return entityList;
    }

    @Override
    public List<T> getByFieldName(String fieldName, Object fieldValue, String orderField, Sort.Direction direction) {
        if (null == direction) {
            direction = Sort.Direction.ASC;
        }
        return this.findAll(getSpec(fieldName, fieldValue), new Sort(direction, orderField));
    }

    @Override
    public List<T> getByFieldName(String fieldName, Object fieldValue, int page, int size, String orderField, Sort.Direction direction) {
        if (null == direction) {
            direction = Sort.Direction.ASC;
        }
        Page<T> pageList = this.findAll(getSpec(fieldName, fieldValue), new PageRequest(page - 1, size, new Sort(direction, orderField)));
        List<T> entityList = new ArrayList<>();
        pageList.forEach((T entity) -> {
            entityList.add(entity);
        });
        return entityList;
    }

    @Override
    public List<T> queryForList(String nativeSql, Object[] args, Class<T> returnClazz) {
        Assert.notNull(nativeSql, "sql不能为空。");
        Query query = entityManager.createNativeQuery(nativeSql, returnClazz);
        for (int i = 0; i < args.length; i++) {
            //参数位置从1开始
            query.setParameter(i + 1, args[i]);
        }
        return query.getResultList();
    }

    @Override
    public List<T> queryForList(String nativeSql, Object[] args, Class<T> returnClazz, int page, int size) {
        Assert.notNull(nativeSql, "sql不能为空。");
        Query query = entityManager.createNativeQuery(nativeSql, returnClazz);
        //设置结果集大小
        query.setMaxResults(size);
        //设置结果集起点
        query.setFirstResult((page - 1) * size);
        for (int i = 0; i < args.length; i++) {
            //参数位置从1开始
            query.setParameter(i + 1, args[i]);
        }
        return query.getResultList();
    }

    @Override
    public List<Map<String, Object>> queryForList(String nativeSql, Object[] args) {
        Assert.notNull(nativeSql, "sql不能为空。");
        Query query = entityManager.createNativeQuery(nativeSql);
        //映射结果为map类型
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        for (int i = 0; i < args.length; i++) {
            //参数位置从1开始
            query.setParameter(i + 1, args[i]);
        }
        return query.getResultList();
    }

    @Override
    public List<Map<String, Object>> queryForList(String nativeSql, Object[] args, int page, int size) {
        Assert.notNull(nativeSql, "sql不能为空。");
        Query query = entityManager.createNativeQuery(nativeSql);
        //映射结果为map类型
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        //设置结果集大小
        query.setMaxResults(size);
        //设置结果集起点
        query.setFirstResult((page - 1) * size);
        for (int i = 0; i < args.length; i++) {
            //参数位置从1开始
            query.setParameter(i + 1, args[i]);
        }
        return query.getResultList();
    }

    @Override
    public void updateByNativeSql(String nativeSql, Object[] args) {
        Assert.notNull(nativeSql, "sql不能为空。");
        Query query = entityManager.createNativeQuery(nativeSql);
        for (int i = 0; i < args.length; i++) {
            //参数位置从1开始
            query.setParameter(i + 1, args[i]);
        }
        query.executeUpdate();
    }
}
