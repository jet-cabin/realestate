//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.jet.realestate.biz.controller;

import com.jet.realestate.biz.client.MessageClient;
import com.jet.realestate.biz.client.UserClient;
import com.jet.realestate.biz.common.MessageParam;
//import com.jet.realestate.biz.service.MessageService;
import com.jet.realestate.biz.model.User;
import com.jet.realestate.biz.vo.Message;
import com.jet.realestate.common.api.CommonPage;
import com.jet.realestate.common.api.CommonResult;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@Api(tags ={ "message ctrl"},value="留言控制器")
@RestController
@RequestMapping({"/v1/message"})
public class MessageController{

  @Autowired
  private UserClient userClient;
  @Autowired
  private MessageClient messageClient;

  public MessageController() {
  }

  @ApiOperation(value = "发表留言")
  @PostMapping
  public CommonResult<Message> create(@RequestBody com.jet.realestate.biz.model.Message msg) {
    msg.setCreateTime(LocalDateTime.now());
    msg.setDisabled(false);
    com.jet.realestate.biz.model.Message message = this.messageClient.create(msg).getData();
    Message result = new Message();
    result.setId(message.getId());
    result.setContent(message.getContent());
    result.setCreateTime(message.getCreateTime());
    result.setDisabled(message.getDisabled());
    result.setHouseId(message.getHouseId());
    User user = (User)userClient.queryUsers(Arrays.asList(message.getUserId())).get(0);
    result.setUsername(user.getUsername());
    return CommonResult.success(result);
  }


@ApiOperation("查询留言")
  @PostMapping({"/list"})
  @ResponseBody
  public CommonResult<CommonPage<Message>> queryMessages(@RequestBody MessageParam param) {
    CommonPage<com.jet.realestate.biz.model.Message> messages = this.messageClient.queryMessages(param).getData();
    if (messages.getTotal() > 0) {
      Set<Long> userIds = (Set)messages.getList().stream().map(com.jet.realestate.biz.model.Message::getUserId).collect(Collectors.toSet());
      List<User> users = userClient.queryUsers(userIds);
      Map<Long, String> userMap = (Map)users.stream().collect(Collectors.toMap(User::getId, User::getUsername));
      List<Message> result = (List)messages.getList().stream().map((m) -> {
        Message msg = new Message();
        msg.setId(m.getId());
        msg.setUsername((String)userMap.get(m.getUserId()));
        msg.setHouseId(m.getHouseId());
        msg.setCreateTime(m.getCreateTime());
        msg.setDisabled(m.getDisabled());
        msg.setContent(m.getContent());
        return msg;
      }).collect(Collectors.toList());
      CommonPage<Message> pageInfo = CommonPage.restPage(result);

      return CommonResult.success(pageInfo);
    } else {
      return CommonResult.success(CommonPage.restPage(Collections.emptyList()));
    }
  }

  @GetMapping({"/{id}"})
  public CommonResult<com.jet.realestate.biz.model.Message> queryMessage(@PathVariable("id") Long id) {
//    com.jet.realestate.biz.model.Message msg = this.messageClient.queryMessage(id);
    return this.messageClient.queryMessage(id);
  }

  @ApiOperation("禁用留言")
  @PutMapping
  public CommonResult<?> disable(@RequestBody com.jet.realestate.biz.model.Message message) {
    this.messageClient.disable(message);
    return CommonResult.success((Object)null);
  }

  @ApiOperation("删除留言")
  @DeleteMapping({"/{id}"})
  public CommonResult<?> delete(@ApiParam("留言主键") @PathVariable("id") Long id) {
    this.messageClient.delete(id);
    return CommonResult.success((Object)null);
  }
}
