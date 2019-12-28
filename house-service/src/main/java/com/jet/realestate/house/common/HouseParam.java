package com.jet.realestate.house.common;

import lombok.Data;

import java.util.Set;

@Data
public class HouseParam extends QueryParam {
    private Boolean includeUnAudit;
    private String title;
    private Long vendorId;
    private Long vendeeId;
    private Boolean audit;
    private Integer priceMin;
    private Integer priceMax;
    private Integer areaMin;
    private Integer areaMax;
    private Boolean newHouse;
    private Layout layout;
    private HouseStatus status;

    private Set<Long> ids;
}
