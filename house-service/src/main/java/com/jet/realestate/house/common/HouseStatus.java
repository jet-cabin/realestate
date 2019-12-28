package com.jet.realestate.house.common;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

public enum HouseStatus {
    AVAILABLE(0),
    RENTED(1),
    ALL(2);

    @JsonValue
    @Getter
    int v;

    HouseStatus(int v) {
        this.v = v;
    }

    public static HouseStatus valueOf(int v) {
        return Arrays.stream(HouseStatus.values()).filter(val -> val.v == v).findFirst().get();
    }
}
