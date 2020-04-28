package com.jet.estate.message.core;

import org.apache.rocketmq.client.producer.MQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
public class Producer {
    @Autowired(required = false)
    private MQProducer innerProducer;

    @PreDestroy
    public void shutdown() {
        if (null != innerProducer)
            this.innerProducer.shutdown();
    }
}
