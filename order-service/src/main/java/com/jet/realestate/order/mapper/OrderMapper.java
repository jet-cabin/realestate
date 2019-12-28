package com.jet.realestate.order.mapper;

import com.jet.realestate.order.model.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper {
    Order queryByCode(@Param("code") String code);

    Order insert(@Param("order") Order order);
}
