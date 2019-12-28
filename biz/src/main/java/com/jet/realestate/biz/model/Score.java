package com.jet.realestate.biz.model;

import lombok.Data;

@Data
public class Score {
    private Long id;
    private Long houseId;
    private Long userId;
    private Integer grade;
}
