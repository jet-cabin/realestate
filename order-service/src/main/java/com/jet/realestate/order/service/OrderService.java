package com.jet.realestate.order.service;

import com.jet.realestate.order.bo.SearchParam;
import com.jet.realestate.order.model.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(Order order);

    Order updatePrice(Integer price);

    Order query(Long id);

    Order queryByCode(String code);

    List<Order> search(SearchParam param);

    Integer delete(Long id);
}
