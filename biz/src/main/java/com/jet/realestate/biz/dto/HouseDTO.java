package com.jet.realestate.biz.dto;

import com.jet.realestate.biz.vo.Rent;
import com.jet.realestate.biz.vo.House;
import lombok.Data;

import java.io.Serializable;

@Data
public class HouseDTO implements Serializable {

    private House house;

    private Rent rent;
}
