package com.jet.realestate.house.mapper;

import com.jet.realestate.house.common.MessageParam;
import com.jet.realestate.house.model.Message;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessageMapper {

    int insert( Message message);

    List<Message> queryMessages(@Param("params") MessageParam param);

    Integer queryMessagesCount(@Param("params") MessageParam param);

    Message queryMessage(@Param("msgId") Long msgId);

    void disable(@Param("msg") Message msg);

    void delete(@Param("msgId") Long msgId);
}
