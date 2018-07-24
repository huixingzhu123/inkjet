package com.zz.inkjet.repository;

import com.zz.framework.repository.DefaultRepository;
import com.zz.inkjet.domain.Product;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zengweiqiang on 2018/5/1.
 */
public interface ProductRepository extends DefaultRepository<Product, String> {

    @Transactional
    void deleteBySystemidIn(String[] systemids);
}
