package com.jet.realestate.biz.common;

import lombok.Getter;

import java.util.Arrays;

public enum OrderStatus {
    NORMAL(0),

    CANCEL(1);

    @Getter
    int v;

    OrderStatus(int v) {
        this.v = v;
    }

    public static OrderStatus valueOf(int v) {
        return Arrays.stream(OrderStatus.values()).filter(o -> o.v == v).findFirst().get();
    }
}
