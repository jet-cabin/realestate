package com.jet.realestate.biz.service.impl;

import com.jet.realestate.biz.client.HouseClient;
import com.jet.realestate.biz.client.OrderClient;
import com.jet.realestate.biz.common.OrderStatus;
import com.jet.realestate.biz.model.House;
import com.jet.realestate.biz.model.Order;
import com.jet.realestate.biz.service.HouseService;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HouseServiceImpl implements HouseService {

    @Autowired
    private HouseClient houseClient;

    @Autowired
    private OrderClient orderClient;

    @Override
    public void rent(Long id, House house) {

        houseClient.rent(id, house);

        Order order=new Order();
        order.setCode(RandomStringUtils.randomAlphanumeric(30));

        order.setHouseId(house.getId());
        order.setVendeeId(house.getVendeeId());
        order.setPrice(house.getPrice());
        order.setStatus(OrderStatus.NORMAL);
        orderClient.createOrder(order);
    }
}
