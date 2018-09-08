/**
 * Copyright(C),2017-2018,广州华资软件技术有限公司
 * FileName: NewsServiceImpl
 * Author: zengweiqiang
 * Date: 2018/5/1  1:05
 * Description:
 * History:
 * <author>     <time>      <version>       <desc>
 * 作者姓名     修改时间        版本号         描述
 */
package com.zz.inkjet.impl;

import com.zz.framework.service.BaseServiceImpl;
import com.zz.inkjet.domain.B_Contact_Msg;
import com.zz.inkjet.domain.News;
import com.zz.inkjet.dto.NewsDto;
import com.zz.inkjet.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zengweiqiang on 2018/5/1.
 */
@Service("NewsService")
public class NewsServiceImpl extends BaseServiceImpl<News> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final NewsRepository newsRepository;

    @Autowired
    public NewsServiceImpl(NewsRepository newsRepository) {
        super(newsRepository);
        this.newsRepository = newsRepository;
    }

    public Page<News> findNewsCriteria(Integer page, Integer size, NewsDto dto) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "createdTime");
        Page<News> newsPage = newsRepository.findAll(new Specification<News>(){
            @Override
            public Predicate toPredicate(Root<News> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(null != dto.getTitle() && !"".equals(dto.getTitle())){
                    list.add(criteriaBuilder.like(root.get("title").as(String.class),"%" + dto.getTitle() + "%"));
                }

                if(null != dto.getStartTime() && !"".equals(dto.getStartTime())) {
                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdTime").as(Date.class),dto.getStartTime()));
                }
                if(null != dto.getEndTime() && !"".equals(dto.getEndTime())) {
                    list.add(criteriaBuilder.lessThan(root.get("createdTime").as(Date.class),dto.getEndTime()));
                }
                list.add(criteriaBuilder.equal(root.get("deleteflag").as(String.class), "0"));
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },pageable);
        return newsPage;
    }
}
