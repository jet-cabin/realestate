package com.jet.realestate.house.mapper;

import com.jet.realestate.house.common.RentParam;
import com.jet.realestate.house.model.Rent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RentMapper {

    int insert(Rent rent);

    List<Rent> queryRents(@Param("params")RentParam param);

    Integer queryRentsCount(@Param("params")RentParam param);

    Rent queryRent(@Param("id") Long id);

    void update(@Param("rent") Rent rent);


}
