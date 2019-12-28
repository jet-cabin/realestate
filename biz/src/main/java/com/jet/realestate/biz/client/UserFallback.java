package com.jet.realestate.biz.client;

import com.jet.realestate.biz.model.User;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class UserFallback implements UserClient {
    @Override
    public List<User> queryUsers(Collection<Long> userIds) {
        return Collections.emptyList();
    }
}
