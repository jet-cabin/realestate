package com.jet.realestate.biz.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class RentParam extends QueryParam{
    private Long houseId;
//    @JsonFormat( timezone = "GMT+8")
@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
//@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginTime;
//    @JsonFormat(timezone = "GMT+8")
@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
//@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
}
