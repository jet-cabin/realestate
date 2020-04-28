package com.jet.realestate.house.consumer;

import com.jet.estate.message.core.annotations.MessageListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

@MessageListener(topic = "tp")
@Slf4j
@Component("mql")
public class MQListener {

    public void onListen(MessageExt obj) {
        log.info("trigger listen handle" + new String(obj.getBody()));
    }
}
