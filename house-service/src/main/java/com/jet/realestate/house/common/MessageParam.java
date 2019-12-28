package com.jet.realestate.house.common;

import lombok.Data;

@Data
public class MessageParam extends QueryParam {
    private Long houseId;
    private Boolean disabled;
}
