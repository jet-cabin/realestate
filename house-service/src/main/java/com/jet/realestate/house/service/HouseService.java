package com.jet.realestate.house.service;

import com.jet.realestate.house.common.HouseParam;
import com.jet.realestate.house.model.House;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface HouseService {

    House create(House house);

    List<House> queryAvailableHouses(HouseParam houseParam);

    House queryHouse(Long id);

    List<House> queryHouses(List<Long> ids);

    void publish(Long id, Long vendorId);

    @Transactional
    void rent(House house);

    void audit(Long id, String phone);

    void update(House house);

    void delete(Long houseId);

    int queryAvailableHousesCount(HouseParam houseParam);

}
