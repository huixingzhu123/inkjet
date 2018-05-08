/**
 * Copyright(C),2017-2018,广州华资软件技术有限公司
 * FileName: UserRepository
 * Author: zengweiqiang
 * Date: 2018/4/25  17:52
 * Description:
 * History:
 * <author>     <time>      <version>       <desc>
 * 作者姓名     修改时间        版本号         描述
 */
package com.zz.inkjet.repository;

import com.zz.framework.repository.DefaultRepository;
import com.zz.inkjet.domain.User;

/**
 * Created by zengweiqiang on 2018/4/25.
 */
public interface UserRepository extends DefaultRepository<User, String> {

    User findByUsernameAndPassword(String username, String password);
}