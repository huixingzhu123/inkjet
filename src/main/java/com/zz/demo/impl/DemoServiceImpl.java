package com.zz.demo.impl;

import com.zz.demo.domain.Demo;
import com.zz.demo.repository.DemoRepository;
import com.zz.framework.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * demo服务类
 * @author yanjunhao
 * @date 2017年10月30日
 */
@Service("DemoService")
public class DemoServiceImpl extends BaseServiceImpl<Demo> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final DemoRepository demoRepository;

    @Autowired
    public DemoServiceImpl(DemoRepository demoRepository) {
        super(demoRepository);
        this.demoRepository = demoRepository;
    }
}
