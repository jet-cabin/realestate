package com.jet.realestate.house.service;

import com.jet.realestate.house.common.RentParam;
import com.jet.realestate.house.model.Rent;

import java.util.List;

public interface RentService {
    Rent create(Rent rent);

    List<Rent> queryRents(RentParam param);

    Integer queryRentsCount(RentParam param);

    Rent query(Long id);

    void update(Rent rent);
}
