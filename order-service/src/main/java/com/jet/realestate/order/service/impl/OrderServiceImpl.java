package com.jet.realestate.order.service.impl;

import com.jet.realestate.order.bo.SearchParam;
import com.jet.realestate.order.mapper.OrderMapper;
import com.jet.realestate.order.model.Order;
import com.jet.realestate.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Order createOrder(Order order) {
        return orderMapper.insert(order);
    }

    @Override
    public Order updatePrice(Integer price) {
        return null;
    }

    @Override
    public Order query(Long id) {
        return null;
    }

    @Override
    public Order queryByCode(String code) {
        return orderMapper.queryByCode(code);
    }

    @Override
    public List<Order> search(SearchParam param) {
        return null;
    }

    @Override
    public Integer delete(Long id) {
        return null;
    }
}
