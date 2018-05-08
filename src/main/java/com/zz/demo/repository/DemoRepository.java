package com.zz.demo.repository;

import com.zz.demo.domain.Demo;
import com.zz.framework.repository.DefaultRepository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * spring data jpa通用写法，接口的中“方法名”按规则进行命名，不需要自行实现
 *
 * @author yanjunhao
 * @date 2017.10.10
 */
public interface DemoRepository extends DefaultRepository<Demo, String> {
    List<Demo> findAllByName(String nameValue);

    /**
     * And用法
     * where demo.age=?1 and demo.name=?2
     *
     * @param age
     * @param name
     * @return
     */
    List<Demo> findByAgeAndName(int age, String name);

    /**
     * Or用法
     * where demo.name=?1 or age=?2
     *
     * @param name
     * @param age
     * @return
     */
    List<Demo> findByNameOrAge(String name, int age);

    /**
     * Between用法
     * where demo.CreatedTime between ?1 and ?2
     *
     * @param start
     * @param end
     * @return
     */
    List<Demo> findByCreatedTimeBetween(Date start, Date end);

    /**
     * LessThan用法
     * where demo.age < ?1
     *
     * @param age
     * @return
     */
    List<Demo> findByAgeLessThan(int age);

    /**
     * GreaterThan用法
     * where demo.age > ?1
     *
     * @param age
     * @return
     */
    List<Demo> findByAgeGreaterThan(int age);

    /**
     * LessThanEqual用法
     * where demo.age <= ?1
     *
     * @param age
     * @return
     */
    List<Demo> findByAgeLessThanEqual(int age);

    /**
     * Before用法（时间）
     * where demo.LastUpdatedTime < ?1
     *
     * @param time
     * @return
     */
    List<Demo> findByLastUpdatedTimeBefore(Date time);

    /**
     * After用法（时间）
     * where demo.LastUpdatedTime > ?1
     *
     * @param time
     * @return
     */
    List<Demo> findByLastUpdatedTimeAfter(Date time);

    /**
     * IsNull用法
     * where demo.name is null
     *
     * @return
     */
    List<Demo> findByNameIsNull();

    /**
     * IsNotNull用法
     * where demo.name is not null
     *
     * @return
     */
    List<Demo> findByNameIsNotNull();

    /**
     * Like用法，参数需要自己加%
     * where demo.name like ?1
     *
     * @param name
     * @return
     */
    List<Demo> findByNameLike(String name);

    /**
     * NotLike用法，参数需要自己加%
     * where demo.name not like ?1
     *
     * @param name
     * @return
     */
    List<Demo> findByNameNotLike(String name);

    /**
     * StartingWith用法
     * where demo.name like ?1%
     *
     * @param name
     * @return
     */
    List<Demo> findByNameStartingWith(String name);


    /**
     * EndingWith用法
     * where demo.name like %?1
     *
     * @param name
     * @return
     */
    List<Demo> findByNameEndingWith(String name);

    /**
     * Containing用法
     * where demo.name like %?1%
     *
     * @param name
     * @return
     */
    List<Demo> findByNameContaining(String name);

    /**
     * OrderBy用法
     * where demo.name = ?1 order by demo.age desc
     *
     * @param name
     * @return
     */
    List<Demo> findByNameOrderByAgeDesc(String name);

    /**
     * Not用法
     * where demo.name <> ?1
     * @param name
     * @return
     */
    List<Demo> findByNameNot(String name);

    /**
     * In用法
     * where demo.age in ?1
     * @param ages
     * @return
     */
    List<Demo> findByAgeIn(Collection<Integer> ages);
}
