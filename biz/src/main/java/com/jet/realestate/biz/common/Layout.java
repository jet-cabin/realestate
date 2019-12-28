package com.jet.realestate.biz.common;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

public enum Layout {
    ONE(0),
    TWO(1),
    THREE(2),
    FOUR(3),
    DUPLEX(4),
    VILLA(5);

    @JsonValue
    @Getter
    int val;

    Layout(int v) {
        val = v;
    }

    public static Layout valueOf(int v) {
        return Arrays.stream(Layout.values()).filter(val -> val.val == v).findFirst().get();
    }
}
