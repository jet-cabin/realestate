package com.jet.realestate.order.controller;

import com.jet.realestate.common.utils.DefaultObjectConverter;
import com.jet.realestate.common.utils.ObjectConverter;
import com.jet.realestate.order.bo.SearchParam;
import com.jet.realestate.order.service.OrderService;
import com.jet.realestate.order.vo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/v1/order")
@RestController
public class OrderController {
    @Autowired
    private OrderService service;

    @PostMapping("/")
    public List<Order> search(@RequestBody SearchParam searchParam) {

        return null;
    }

    @GetMapping("/{code}")
    public Order queryByCode(@RequestParam(value = "code", required = true) String code) {
        com.jet.realestate.order.model.Order o = service.queryByCode(code);

        ObjectConverter<com.jet.realestate.order.model.Order, Order> converter = new DefaultObjectConverter();

        Order result = converter.convert(o, Order.class, null);

        return result;
    }

    @PostMapping
    public Order createOrder(@RequestBody com.jet.realestate.order.model.Order order) {
        com.jet.realestate.order.model.Order o = service.createOrder(order);
        ObjectConverter<com.jet.realestate.order.model.Order, Order> converter = new DefaultObjectConverter<>();

        Order result = converter.convert(o, Order.class, null);
        return result;
    }
}
