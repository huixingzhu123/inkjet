package com.zz.framework.event.service;

import com.zz.framework.event.domin.EventPublish;
import com.zz.framework.event.repository.EventPublishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventPublishService{

    @Autowired
    EventPublishRepository evenpublishrepository;

    @Transactional
    public  EventPublish save(EventPublish evenpublish){
        return evenpublishrepository.save(evenpublish);
    }

}
