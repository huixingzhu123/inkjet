/**
 * Copyright(C),2017-2018,广州华资软件技术有限公司
 * FileName: ProductServiceImpl
 * Author: zengweiqiang
 * Date: 2018/5/1  1:04
 * Description:
 * History:
 * <author>     <time>      <version>       <desc>
 * 作者姓名     修改时间        版本号         描述
 */
package com.zz.inkjet.impl;

import com.zz.framework.service.BaseServiceImpl;
import com.zz.inkjet.domain.Product;
import com.zz.inkjet.repository.ProductRepository;
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
import java.util.List;
import java.util.Map;

/**
 * Created by zengweiqiang on 2018/5/1.
 */
@Service("ProductService")
public class ProductServiceImpl extends BaseServiceImpl<Product> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        super(productRepository);
        this.productRepository = productRepository;
    }

    public Page<Product> findProductNoCriteria(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "systemid");
        return productRepository.findAll(pageable);
    }

    public Page<Product> findProductCriteria(Integer page, Integer size, Product product) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "systemid");
        Page<Product> productPage = productRepository.findAll(new Specification<Product>(){
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(null != product.getItemId() && !"".equals(product.getItemId())){
                    list.add(criteriaBuilder.equal(root.get("itemId").as(String.class),product.getItemId()));
                }
                if(null != product.getCartridgeType() && !"".equals(product.getCartridgeType())) {
                    list.add(criteriaBuilder.equal(root.get("cartridgeType").as(String.class),product.getCartridgeType()));

                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },pageable);
        return productPage;
    }
}
