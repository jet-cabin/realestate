package com.jet.realestate.biz.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jet.realestate.security.model.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
//@Builder
//@NoArgsConstructor
public class Rent {
    private Long id;
    private House house;
    private User vendee;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime createTime;
    private Integer price;

//    public Rent(){}
}
