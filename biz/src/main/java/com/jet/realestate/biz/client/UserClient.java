package com.jet.realestate.biz.client;

import com.jet.realestate.biz.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.List;

@FeignClient(serviceId = "estate-basic",path ="/v1/user",fallback = UserFallback.class)
public interface UserClient {

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @ResponseBody
    List<User> queryUsers(Collection<Long> userIds);
}
