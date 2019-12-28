package com.jet.realestate.biz.service;

import com.jet.realestate.biz.model.House;

public interface HouseService {
    void rent(Long id, House house);
}
