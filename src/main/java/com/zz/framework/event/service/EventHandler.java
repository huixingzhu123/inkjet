package com.zz.framework.event.service;

import com.zz.framework.event.domin.EventPublish;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.event.Event;
import reactor.spring.annotation.Consumer;
import reactor.spring.annotation.ReplyTo;
import reactor.spring.annotation.Selector;


@Consumer
public class EventHandler {

    private static final Logger logger = LoggerFactory.getLogger(EventHandler.class);

    @Autowired
    com.zz.framework.event.service.EventPublishService eventservice;

    @Selector(value = "eventpublish", reactor = "@rootReactor")
    @ReplyTo(value="ReplyResult")
    public String handleTestTopic(Event<EventPublish> evt){
        logger.info("eventpublish running");
        String systemid = null;
        try {
            EventPublish eventpub =  eventservice.save(evt.getData());
            systemid = eventpub.getSystemid();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("eventpublish running error:"+e.getMessage());
        }
        return systemid;
    }

    @Selector(value="ReplyResult",reactor="@rootReactor")
    public void onTestSayhitResult1(String result){
        logger.info("ReplyResult is:"+result);
    }
}
