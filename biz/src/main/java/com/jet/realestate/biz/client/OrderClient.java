package com.jet.realestate.biz.client;

import com.jet.realestate.biz.common.OrderSearchParam;
import com.jet.realestate.biz.model.Order;
import com.jet.realestate.common.utils.DefaultObjectConverter;
import com.jet.realestate.common.utils.ObjectConverter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(serviceId = "order-service", path = "/v1/order")
public interface OrderClient {
    @PostMapping("/")
    List<Order> search(@RequestBody OrderSearchParam searchParam);

    @GetMapping("/{code}")
    Order queryByCode(@RequestParam(value = "code", required = true) String code);

    @PostMapping
    Order createOrder(@RequestBody Order order);
}
