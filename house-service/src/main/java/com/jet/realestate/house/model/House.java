package com.jet.realestate.house.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jet.realestate.house.common.HouseStatus;
import com.jet.realestate.house.common.Layout;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;


/**
 * 房屋实体
 */
@Data
public class House {

    /**
     * 主键
     */
    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 户型
     */
    private Layout layout;
    /**
     * 状态
     */
    private HouseStatus status;
    /**
     * 价格
     */
    private int price;
    /**
     * 面积
     */
    private int area;
    /**
     * 备注
     */
    private String note;

    /**
     * 业主
     */
    private Long vendorId;
    /**
     * 需求方
     */
    private Long vendeeId;
    /**
     * 是否发布
     */
    private boolean publish;
    /**
     * 是否审核
     */
    private boolean audit;
    /**
     * 审核时间
     */
    private Date auditTime;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime publishTime;


    private String address;

    private boolean newHouse;

    private String contactPhone;
}
