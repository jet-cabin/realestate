//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.jet.realestate.house.service.impl;

import com.jet.realestate.house.common.MessageParam;
import com.jet.realestate.house.mapper.MessageMapper;
import com.jet.realestate.house.model.Message;
import com.jet.realestate.house.service.MessageService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;

    public MessageServiceImpl() {
    }

    public Message insert(Message message) {
        this.messageMapper.insert(message);
        return message;
    }

    public List<Message> queryMessages(MessageParam param) {
        return this.messageMapper.queryMessages(param);
    }

    public Message queryMessage(Long msgId) {
        return this.messageMapper.queryMessage(msgId);
    }

    public Integer queryMessagesCount(MessageParam param) {
        return this.messageMapper.queryMessagesCount(param);
    }

    public void disable(Message msg) {
        this.messageMapper.disable(msg);
    }

    public void delete(Long msgId) {
        this.messageMapper.delete(msgId);
    }
}
