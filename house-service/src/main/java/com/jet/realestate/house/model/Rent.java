package com.jet.realestate.house.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
//@Builder
public class Rent {
    Long id;
    Long houseId;
    Long vendeeId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    LocalDateTime createTime;
    Integer price;
    public Rent(){}
}
