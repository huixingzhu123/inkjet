package com.zz.framework.event;

import com.zz.framework.domain.BaseEntity;
import com.zz.framework.event.domin.EventPublish;
import com.zz.framework.util.DateUtil;
import com.zz.framework.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.Reactor;
import reactor.event.Event;

@Component
public class EventRegistry{

    private static final Logger logger = LoggerFactory.getLogger(EventRegistry.class);

    @Autowired
    @Qualifier("rootReactor")
    private Reactor reactor;

    /**
     * 传入发布的对象，事件状态，事件类型
     * @param obj
     * @param status
     * @param eventtype
     */
    public void publish(BaseEntity obj,String status,String eventtype){
        EventPublish eventpublish = new EventPublish();
        eventpublish.setStatus(status);
        eventpublish.setEventId(obj.getSystemid());
        eventpublish.setPayload(JsonUtils.object2Json(obj));
        eventpublish.setCreatedTime(DateUtil.getSystemLocalTime());
        eventpublish.setEventType(eventtype);
        reactor.notify("eventpublish", Event.wrap(eventpublish));
    }
}
