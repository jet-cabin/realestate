//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.jet.realestate.house.controller;

import com.jet.realestate.house.common.MessageParam;
import com.jet.realestate.house.model.User;
import com.jet.realestate.house.service.MessageService;
import com.jet.realestate.house.model.Message;
import com.jet.realestate.common.api.CommonPage;
import com.jet.realestate.common.api.CommonResult;
//import com.jet.realestate.security.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Api(tags = {"message ctrl"}, value = "留言控制器")
@RestController
@RequestMapping({"/v1/message"})
public class MessageController {


    @Autowired
    private MessageService svc;

    public MessageController() {
    }

    @ApiOperation(value = "发表留言")
    @PostMapping
    public CommonResult<Message> create(@RequestBody com.jet.realestate.house.model.Message msg) {
        msg.setCreateTime(LocalDateTime.now());
        msg.setDisabled(false);
        com.jet.realestate.house.model.Message message = this.svc.insert(msg);
        Message result = new Message();
        result.setId(message.getId());
        result.setContent(message.getContent());
        result.setCreateTime(message.getCreateTime());
        result.setDisabled(message.getDisabled());
        result.setHouseId(message.getHouseId());

        return CommonResult.success(result);
    }


    @ApiOperation("查询留言")
    @PostMapping({"/list"})
    @ResponseBody
    public CommonResult<CommonPage<Message>> queryMessages(@RequestBody MessageParam param) {
        List<com.jet.realestate.house.model.Message> messages = this.svc.queryMessages(param);

        CommonPage<Message> pageInfo = CommonPage.restPage(messages);
        Long total = (long) this.svc.queryMessagesCount(param);
        pageInfo.setTotal(total);
        return CommonResult.success(pageInfo);
    }

    @GetMapping({"/{id}"})
    public CommonResult<com.jet.realestate.house.model.Message> queryMessage(@PathVariable("id") Long id) {
        com.jet.realestate.house.model.Message msg = this.svc.queryMessage(id);
        return CommonResult.success(msg);
    }

    @ApiOperation("禁用留言")
    @PutMapping
    public CommonResult<?> disable(@RequestBody com.jet.realestate.house.model.Message message) {
        this.svc.disable(message);
        return CommonResult.success((Object) null);
    }

    @ApiOperation("删除留言")
    @DeleteMapping({"/{id}"})
    public CommonResult<?> delete(@ApiParam("留言主键") @PathVariable("id") Long id) {
        this.svc.delete(id);
        return CommonResult.success((Object) null);
    }
}
