package com.jet.realestate.biz.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Message implements Serializable {
    private Long id;
    private Long houseId;
    private Long userId;
    private String content;
    private Boolean disabled;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public LocalDateTime getCreateTime() {
        return createTime;
    }


    private LocalDateTime createTime;
}
