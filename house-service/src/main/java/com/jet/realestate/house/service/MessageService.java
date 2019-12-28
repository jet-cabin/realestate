package com.jet.realestate.house.service;

import com.jet.realestate.house.common.MessageParam;
import com.jet.realestate.house.model.Message;
import java.util.List;

public interface MessageService {
    Message insert(Message var1);

    List<Message> queryMessages(MessageParam var1);

    Message queryMessage(Long var1);

    Integer queryMessagesCount(MessageParam var1);

    void disable(Message var1);

    void delete(Long var1);
}