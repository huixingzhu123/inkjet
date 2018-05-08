package com.zz.framework.security;

import com.zz.framework.util.DateUtil;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Calendar;


/**
 * 实体审计功能类
 * 给AuditEntity返回用户和时间
 * @author yanjunhao
 * @date 2017.10.10
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<String>,DateTimeProvider {
    @Override
    public String getCurrentAuditor() {
        return SecurityUtil.getCurrentUserId();
    }

    @Override
    public Calendar getNow() {
        Calendar c = Calendar.getInstance();
        c.setTime(DateUtil.getDatabaseLocalTime());
        return c;
    }
}
