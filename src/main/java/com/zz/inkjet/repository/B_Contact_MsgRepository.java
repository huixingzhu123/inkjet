package com.zz.inkjet.repository;

import com.zz.framework.repository.DefaultRepository;
import com.zz.inkjet.domain.B_Contact_Msg;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * spring data jpa通用写法，接口的中“方法名”按规则进行命名，不需要自行实现
 *
 * @author yanjunhao
 * @date 2017.10.10
 */
public interface B_Contact_MsgRepository extends DefaultRepository<B_Contact_Msg, String> {
    List<B_Contact_Msg> findAllByName(String nameValue);

    @Transactional
    void deleteBySystemidIn(String[] ids);
}
