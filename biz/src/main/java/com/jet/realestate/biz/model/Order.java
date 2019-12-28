package com.jet.realestate.biz.model;


import com.jet.realestate.biz.common.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Order {
    private Long id;

    private String code;

    private LocalDateTime createTime;

    private Long vendeeId;

    private Long houseId;

    private Integer price;

    private String note;

    private OrderStatus status;
}
