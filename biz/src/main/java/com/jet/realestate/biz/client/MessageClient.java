package com.jet.realestate.biz.client;

import com.jet.realestate.biz.common.MessageParam;
import com.jet.realestate.biz.model.Message;
import com.jet.realestate.common.api.CommonPage;
import com.jet.realestate.common.api.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "msgClient",serviceId = "house-service", path = "/v1/message")
public interface MessageClient {
    @RequestMapping
    CommonResult<Message> create(@RequestBody com.jet.realestate.biz.model.Message msg);


    @RequestMapping({"/list"})
    @ResponseBody
    CommonResult<CommonPage<Message>> queryMessages(@RequestBody MessageParam param);

    @RequestMapping({"/{id}"})
    CommonResult<com.jet.realestate.biz.model.Message> queryMessage(@PathVariable("id") Long id);


    @RequestMapping(method = RequestMethod.PUT)
    CommonResult<?> disable(@RequestBody com.jet.realestate.biz.model.Message message);

    @RequestMapping(method = RequestMethod.DELETE)
    CommonResult<?> delete(@PathVariable("id") Long id);
}
