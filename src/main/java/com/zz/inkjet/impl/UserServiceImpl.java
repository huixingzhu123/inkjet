/**
 * Copyright(C),2017-2018,广州华资软件技术有限公司
 * FileName: UserServiceImpl
 * Author: zengweiqiang
 * Date: 2018/4/25  17:50
 * Description:
 * History:
 * <author>     <time>      <version>       <desc>
 * 作者姓名     修改时间        版本号         描述
 */
package com.zz.inkjet.impl;

import com.zz.framework.service.BaseServiceImpl;
import com.zz.inkjet.domain.User;
import com.zz.inkjet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zengweiqiang on 2018/4/25.
 */
@Service("UserService")
public class UserServiceImpl extends BaseServiceImpl<User> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    public User findByUsernameAndPassword(String username, String password) {
        List<User> list =  userRepository.queryForList("select * from user where username = ? and password = ?",new Object[]{username,password},User.class);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }
}
