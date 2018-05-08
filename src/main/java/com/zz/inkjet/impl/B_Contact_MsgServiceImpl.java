package com.zz.inkjet.impl;

import com.zz.framework.service.BaseServiceImpl;
import com.zz.inkjet.domain.B_Contact_Msg;
import com.zz.inkjet.repository.B_Contact_MsgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

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
}
