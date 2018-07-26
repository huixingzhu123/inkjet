package com.zz.inkjet.impl;

import com.zz.framework.service.BaseServiceImpl;
import com.zz.inkjet.domain.B_Contact_Msg;
import com.zz.inkjet.domain.Product;
import com.zz.inkjet.dto.MessageDto;
import com.zz.inkjet.repository.B_Contact_MsgRepository;
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
 * demo服务类
 * @author yanjunhao
 * @date 2017年10月30日
 */
@Service("B_Contact_MsgService")
public class B_Contact_MsgServiceImpl extends BaseServiceImpl<B_Contact_Msg> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final B_Contact_MsgRepository b_Contact_MsgRepository;

    @Autowired
    public B_Contact_MsgServiceImpl(B_Contact_MsgRepository b_Contact_MsgRepository) {
        super(b_Contact_MsgRepository);
        this.b_Contact_MsgRepository = b_Contact_MsgRepository;
    }

    public Page<B_Contact_Msg> findMsgCriteria(Integer page, Integer size, MessageDto dto) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "createdTime");
        Page<B_Contact_Msg> msgPage = b_Contact_MsgRepository.findAll(new Specification<B_Contact_Msg>(){
            @Override
            public Predicate toPredicate(Root<B_Contact_Msg> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(null != dto.getName() && !"".equals(dto.getName())){
                    list.add(criteriaBuilder.like(root.get("name").as(String.class),"%" + dto.getName() + "%"));
                }
                if(null != dto.getEmail() && !"".equals(dto.getEmail())){
                    list.add(criteriaBuilder.like(root.get("email").as(String.class), "%" + dto.getEmail() + "%"));
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
        return msgPage;
    }

    public void physicalRemove(String[] ids) {
        b_Contact_MsgRepository.deleteBySystemidIn(ids);
    }
}
